package net.novate.base.binding

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindings {
    @JvmStatic
    @BindingAdapter("enabled")
    fun setEnabled(view: View, enabled: Boolean) {
        view.isEnabled = enabled
    }

    @JvmStatic
    @BindingAdapter("selected")
    fun setSelected(view: View, selected: Boolean) {
        view.isSelected = selected
    }
}