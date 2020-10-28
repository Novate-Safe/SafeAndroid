package net.novate.base.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private const val DEFAULT_SHARED_PREFERENCES = "DefaultSharedPreferences"

// 通过委托的方式来使用 SharedPreferences

/**
 * 获取 SharedPreferences String 类型的委托属性
 */
fun Context.sharedString(
    key: String,
    preference: String = DEFAULT_SHARED_PREFERENCES,
    default: () -> String = { "" }
) = object : SharedPreferencesProperty<String>(this, preference) {

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return sharedPreferences.getString(key, default()) as String
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}

/**
 * 获取 SharedPreferences StringSet 类型的委托属性
 */
fun Context.sharedStringSet(
    key: String,
    preference: String = DEFAULT_SHARED_PREFERENCES,
    default: () -> Set<String> = { emptySet() }
) = object : SharedPreferencesProperty<Set<String>>(this, preference) {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Set<String> {
        return sharedPreferences.getStringSet(key, default()) as Set<String>
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Set<String>) {
        sharedPreferences.edit().putStringSet(key, value).apply()
    }
}

/**
 * 获取 SharedPreferences Int 类型的委托属性
 */
fun Context.sharedInt(
    key: String,
    preference: String = DEFAULT_SHARED_PREFERENCES,
    default: () -> Int = { 0 }
) = object : SharedPreferencesProperty<Int>(this, preference) {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return sharedPreferences.getInt(key, default())
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }
}

/**
 * 获取 SharedPreferences Long 类型的委托属性
 */
fun Context.sharedLong(
    key: String,
    preference: String = DEFAULT_SHARED_PREFERENCES,
    default: () -> Long = { 0L }
) = object : SharedPreferencesProperty<Long>(this, preference) {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Long {
        return sharedPreferences.getLong(key, default())
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }
}

/**
 * 获取 SharedPreferences Float 类型的委托属性
 */
fun Context.sharedFloat(
    key: String,
    preference: String = DEFAULT_SHARED_PREFERENCES,
    default: () -> Float = { 0F }
) = object : SharedPreferencesProperty<Float>(this, preference) {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Float {
        return sharedPreferences.getFloat(key, default())
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }
}

/**
 * 获取 SharedPreferences Boolean 类型的委托属性
 */
fun Context.sharedBoolean(
    key: String,
    preference: String = DEFAULT_SHARED_PREFERENCES,
    default: () -> Boolean = { false }
) = object : SharedPreferencesProperty<Boolean>(this, preference) {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return sharedPreferences.getBoolean(key, default())
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }
}

abstract class SharedPreferencesProperty<T>(
    context: Context,
    preference: String
) : ReadWriteProperty<Any?, T> {
    protected val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(preference, Context.MODE_PRIVATE)
    }
}