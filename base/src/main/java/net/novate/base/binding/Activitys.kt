package net.novate.base.binding

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * TODO
 */
inline fun <reified T : ViewDataBinding> Activity.bindContentView(@LayoutRes layoutId: Int) = DataBindingUtil.setContentView<T>(this, layoutId)