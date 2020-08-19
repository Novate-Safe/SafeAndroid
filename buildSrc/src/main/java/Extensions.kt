import java.util.*
import kotlin.collections.HashMap

/**
 * 扩展属性映射表，映射对象和其额外属性表；使用 WeakHashMap，当对象无其他引用时，自动丢弃其额外属性
 *
 * Note:
 * 1. WeakHashMap 应当换为 WeakIdentityMap, 避免某些对象重写 hashCode 和 equals 方法导致数据错乱
 * 2. WeakHashMap 应当换为线程安全的 HashMap, 避免多线程操作导致数据错乱
 * @author Gavin Novate
 */
private val EXTENSIONS by lazy { WeakHashMap<Any, MutableMap<String, Any>>() }

/**
 * 设置属性
 */
fun Any.setExtension(name: String, value: Any) {
    EXTENSIONS.getOrPut(this) { HashMap() }[name] = value
}

/**
 * 获取属性
 */
@Suppress("UNCHECKED_CAST")
fun <T> Any.getExtension(name: String, default: () -> T): T {
    return EXTENSIONS.getOrPut(this) { HashMap() }.getOrPut(name) { default() as Any } as T
}