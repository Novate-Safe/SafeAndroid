package net.novate.base.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * 差异计算器接口，用于 DiffUtil 进行差异计算
 */
interface DiffCalculator<T> {
    fun areItemsTheSame(old: T, new: T): Boolean
    fun areContentsTheSame(old: T, new: T): Boolean
    fun getChangePayload(old: T, new: T): Any? = null
}

abstract class BaseBindingAdapter<D, B : ViewDataBinding> : RecyclerView.Adapter<BaseBindingViewHolder<B>>(), DiffCalculator<D> {

    var data = listOf<D>()
        set(value) {
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = data.size

                override fun getNewListSize(): Int = value.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@BaseBindingAdapter.areItemsTheSame(data[oldItemPosition], value[newItemPosition])
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@BaseBindingAdapter.areContentsTheSame(data[oldItemPosition], value[newItemPosition])
                }

                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                    return this@BaseBindingAdapter.getChangePayload(data[oldItemPosition], value[newItemPosition])
                }
            }).dispatchUpdatesTo(this)
            field = value.toList()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<B> {
        return BaseBindingViewHolder(
            onCreateViewDataBinding(LayoutInflater.from(parent.context), parent, viewType)
        )
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<B>, position: Int) {
        onBindViewDataBinding(holder.binding, data[position], position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = data.size

    protected abstract fun onCreateViewDataBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): B

    protected abstract fun onBindViewDataBinding(binding: B, item: D, position: Int)
}