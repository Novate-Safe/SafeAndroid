import com.google.gson.Gson
import org.gradle.api.Project
import java.util.*
import kotlin.collections.HashMap

/**
 * Gradle Project gson
 */
val Project.gson
    get() = getExtension("gson") { Gson() }

/**
 * Gradle Project localProperties
 */
val Project.localProperties
    get() = getExtension("localProperties") {
        rootProject.file("local.properties").inputStream().use {
            Properties()
                .apply { load(it) }.entries
                .fold(HashMap<String, String>()) { map, (key, value) ->
                    map.apply { put(key as String, value as String) }
                }
        }
    }

/**
 * 读取常规格式的属性（支持 Json）
 *
 * 如：
 * data.property=property
 */
inline fun <reified T> Project.localProperty(name: String): T? {
    return with(localProperties[name]) {
        if (this is T) {
            this
        } else {
            gson.fromJson(this, T::class.java)
        }
    }
}

/**
 * 读取 Map 格式的属性（支持 Json）
 *
 * 如：
 * data.property[0]=property0
 * data.property[1]=property1
 * data.property[2]=property2
 */
inline fun <reified T> Project.localPropertyList(name: String): List<T?> {
    return with(localProperties.keys
        .asSequence()
        // 过滤出符合 "name[key]" 格式的属性
        .filter { it.startsWith("$name[") && it.endsWith("]") }
        // 剥离 "name[" 和 "]" 保留 key
        .map { it.substring("$name[".lastIndex + 1 until it.lastIndex) }
        // 尝试转为为索引，失败则标记为 -1
        .map {
            try {
                it.toInt()
            } catch (e: NumberFormatException) {
                -1
            }
        }
        // 过滤出大于等于 0 的索引
        .filter { it >= 0 }
        .toList()
    ) {
        // 创建一个以索引最大值 +1 为长度的列表
        List((max() ?: -1) + 1) {
            // 初始化列表，若包含当前索引则尝试读取值，否则赋值为 null
            if (contains(it)) {
                localProperty<T>("$name[$it]")
            } else {
                null
            }
        }
    }
}

/**
 * 读取 Map 格式的属性（支持 Json）
 *
 * 如 ('\[' 转义为 '[' ) ：
 * data.property\[a]=propertyA
 * data.property\[b]=propertyB
 * data.property\[c]=propertyC
 */
inline fun <reified T> Project.localPropertyMap(name: String): Map<String, T?> {
    return localProperties.keys
        .asSequence()
        // 过滤出符合 "name[key]" 格式的属性
        .filter { it.startsWith("$name[") && it.endsWith("]") }
        // 剥离 "name[" 和 "]" 保留 key
        .map { it.substring("$name[".lastIndex + 1 until it.lastIndex) }
        // 生成 key value 键值对
        .map { it to localProperty<T>("$name[$it]") }
        .toMap()
}