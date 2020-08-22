package net.novate.base.ui.system

import android.view.View
import android.view.Window

/**
 * 调暗状态栏和导航栏
 */
var Window.lowProfileSystemUi: Boolean
    get() = hasSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LOW_PROFILE)
    set(value) = if (value) {
        addSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LOW_PROFILE)
    } else {
        removeSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LOW_PROFILE)
    }

/**
 * 让内容显示在状态栏后面；可以配合 BaseTheme.Light 和 BaseTheme.Night 使用
 */
var Window.appearBehindStatusBar: Boolean
    get() = hasSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    set(value) = if (value) {
        addSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    } else {
        if (appearBehindNavigationBar){
            removeSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }else{
            removeSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }

/**
 * 让内容显示在导航栏后面；可以配合 BaseTheme.Light 和 BaseTheme.Night 使用
 */
var Window.appearBehindNavigationBar: Boolean
    get() = hasSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    set(value) = if (value) {
        addSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    } else {
        if (appearBehindStatusBar) {
            removeSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        } else {
            removeSystemUiVisibilityFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }

fun Window.addSystemUiVisibilityFlags(flags: Int) {
    decorView.systemUiVisibility = decorView.systemUiVisibility or flags
}

fun Window.removeSystemUiVisibilityFlags(flags: Int) {
    decorView.systemUiVisibility = decorView.systemUiVisibility and flags.inv()
}

fun Window.hasSystemUiVisibilityFlags(flags: Int): Boolean {
    return decorView.systemUiVisibility and flags == flags
}