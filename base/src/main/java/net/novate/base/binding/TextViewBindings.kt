package net.novate.base.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import net.novate.base.widget.edittext.OnTextChangedListener

/**
 * 添加文本变化监听器
 */
@BindingAdapter("onTextChanged")
fun TextView.addOnTextChangedListener(onTextChangedListener: OnTextChangedListener) {
    addTextChangedListener(onTextChangedListener)
}