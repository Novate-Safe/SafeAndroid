package net.novate.base.binding

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * TODO
 */
fun <T : ViewDataBinding> Activity.bindContentView(@LayoutRes layoutId: Int): T = DataBindingUtil.setContentView(this, layoutId)