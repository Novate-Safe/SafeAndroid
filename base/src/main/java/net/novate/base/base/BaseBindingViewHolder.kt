package net.novate.base.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * 用于 RecyclerView 通用的基于 DataBinding 的 ViewHolder
 */
open class BaseBindingViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)