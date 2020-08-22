package net.novate.base.ui

import android.view.View
import android.view.Window

/**
 * 让内容显示在状态栏后面；可以配合 BaseTheme.Light 和 BaseTheme.Night 使用
 */
fun Window.appearBehindStatusBar() {
    decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}

/**
 * 让内容显示在导航栏后面；可以配合 BaseTheme.Light 和 BaseTheme.Night 使用
 */
fun Window.appearBehindNavigationBar() {
    decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}