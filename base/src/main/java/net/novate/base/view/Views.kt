package net.novate.base.view

import android.view.View
import android.view.ViewParent

/**
 * TODO
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