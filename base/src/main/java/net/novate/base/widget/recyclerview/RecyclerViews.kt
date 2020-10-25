package net.novate.base.widget.recyclerview

import androidx.recyclerview.widget.RecyclerView

/**
 * 通知数据变更，刷新界面；只刷条目内容，不调整条目次序。
 *
 * @see RecyclerView.Adapter.notifyDataSetChanged
 * @see RecyclerView.Adapter.notifyItemRangeChanged
 */
fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.notifyDataContentChanged() {
    notifyItemRangeChanged(0, itemCount)
}