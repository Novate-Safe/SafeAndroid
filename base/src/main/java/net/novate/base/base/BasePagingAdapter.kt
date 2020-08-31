package net.novate.base.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter


abstract class BasePagingAdapter<D : Any, B : ViewDataBinding>(diffCalculator: DiffCalculator<D>) : PagingDataAdapter<D, BaseBindingViewHolder<B>>(DiffCalculatorCallback(diffCalculator)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<B> {
        return BaseBindingViewHolder(onCreateViewBinding(LayoutInflater.from(parent.context), parent, viewType))
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<B>, position: Int) {
        onBindViewBinding(holder.binding, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    protected abstract fun onCreateViewBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): B

    protected abstract fun onBindViewBinding(binding: B, item: D?, position: Int)
}

abstract class BaseDiffPagingAdapter<D : DiffCalculable<D>, B : ViewDataBinding> : PagingDataAdapter<D, BaseBindingViewHolder<B>>(DiffCalculableCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<B> {
        return BaseBindingViewHolder(onCreateViewBinding(LayoutInflater.from(parent.context), parent, viewType))
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<B>, position: Int) {
        onBindViewBinding(holder.binding, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    protected abstract fun onCreateViewBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): B

    protected abstract fun onBindViewBinding(binding: B, item: D?, position: Int)
}