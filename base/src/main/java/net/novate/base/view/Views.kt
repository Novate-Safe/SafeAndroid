package net.novate.base.view

import android.view.View
import android.view.ViewParent
import androidx.annotation.IdRes

/**
 * 寻找最近一个类型匹配的父控件
 */
inline fun <reified T : View> View.findParent(): T? {
    var viewParent: ViewParent? = parent
    while (viewParent is View) {
        if (viewParent is T) {
            return viewParent
        } else {
            viewParent = (viewParent as View).parent
        }
    }
    return null
}

/**
 * 寻找最近一个 ID 匹配的父控件
 */
@Suppress("UNCHECKED_CAST")
fun <T : View> View.findParent(@IdRes id: Int): T? {
    var viewParent: ViewParent? = parent
    while (viewParent is View) {
        if (viewParent.id == id) {
            return viewParent as T
        } else {
            viewParent = (viewParent as View).parent
        }
    }
    return null
}

/**
 * 设置属性
 */
@Suppress("UNCHECKED_CAST")
fun View.setExtension(@IdRes name: Int, value: Any?) {
    setTag(name, value)
}

/**
 * 获取属性
 */
@Suppress("UNCHECKED_CAST")
fun <T> View.getExtension(@IdRes name: Int, default: () -> T): T {
    return (getTag(name) ?: default().also { setTag(name, it) }) as T
}