package net.novate.base.binding

import android.graphics.drawable.Drawable
import androidx.annotation.Dimension
import androidx.annotation.IntRange
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.novate.base.base.BaseBindingAdapter
import net.novate.base.base.BasePagingAdapter
import net.novate.base.widget.recyclerview.GridLayoutSpacingItemDecoration
import net.novate.base.widget.recyclerview.LinearLayoutDividerItemDecoration
import net.novate.base.widget.recyclerview.LinearLayoutSpacingItemDecoration

/**
 * 设置 RecyclerView 的数据
 */
@BindingAdapter("adapter", "data", requireAll = false)
fun <D, B : ViewDataBinding> RecyclerView.setData(adapter: BaseBindingAdapter<D, B>, data: List<D>? = null) {
    adapter.data = data ?: listOf()
    if (this.adapter !== adapter) {
        this.adapter = adapter
    }
}


@BindingAdapter("lifecycle", "adapter", "data", requireAll = false)
fun <D : Any, B : ViewDataBinding> RecyclerView.setData(lifecycle: Lifecycle, adapter: BasePagingAdapter<D, B>, data: PagingData<D>?) {
    // TODO: 2020/8/31 此处有问题 不应该频繁 launch
    lifecycle.coroutineScope.launchWhenCreated {
        data?.let { adapter.submitData(data) }
    }
    if (this.adapter !== adapter) {
        this.adapter = adapter
    }
}

@BindingAdapter(
    "linearLayout_orientation",
    "linearLayout_reverseLayout",
    "linearLayout_divider",
    "linearLayout_showLastDivider",
    requireAll = false
)
fun RecyclerView.setLinearLayoutManager(
    @RecyclerView.Orientation orientation: Int?,
    reverseLayout: Boolean,
    divider: Drawable,
    showLastDivider: Boolean = false
) {
    // 设置 LinearLayoutManager
    layoutManager.apply {
        if (this is LinearLayoutManager) {
            setOrientation(orientation ?: RecyclerView.VERTICAL)
            setReverseLayout(reverseLayout)
        } else {
            layoutManager = LinearLayoutManager(context, orientation ?: RecyclerView.VERTICAL, reverseLayout)
        }
    }
    // 设置 LinearLayoutDividerItemDecoration
    var found = false
    for (index in 0 until itemDecorationCount) {
        val itemDecoration = getItemDecorationAt(index)
        if (itemDecoration is LinearLayoutDividerItemDecoration) {
            itemDecoration.reset(orientation ?: RecyclerView.VERTICAL, divider, showLastDivider).let { if (it) invalidateItemDecorations() }
            found = true
            break
        }
    }
    if (!found) {
        addItemDecoration(LinearLayoutDividerItemDecoration(orientation ?: RecyclerView.VERTICAL, divider, showLastDivider))
    }
}

@BindingAdapter(
    "linearLayout_orientation",
    "linearLayout_reverseLayout",
    "linearLayout_startSpacing",
    "linearLayout_topSpacing",
    "linearLayout_endSpacing",
    "linearLayout_bottomSpacing",
    "linearLayout_innerSpacing",
    requireAll = false
)
fun RecyclerView.setLinearLayoutManager(
    @RecyclerView.Orientation orientation: Int?,
    reverseLayout: Boolean,
    @Dimension startSpacing: Float,
    @Dimension topSpacing: Float,
    @Dimension endSpacing: Float,
    @Dimension bottomSpacing: Float,
    @Dimension innerSpacing: Float
) {
    // 设置 LinearLayoutManager
    layoutManager.apply {
        if (this is LinearLayoutManager) {
            setOrientation(orientation ?: RecyclerView.VERTICAL)
            setReverseLayout(reverseLayout)
        } else {
            layoutManager = LinearLayoutManager(context, orientation ?: RecyclerView.VERTICAL, reverseLayout)
        }
    }
    // 设置 LinearLayoutSpacingItemDecoration
    var found = false
    for (index in 0 until itemDecorationCount) {
        val itemDecoration = getItemDecorationAt(index)
        if (itemDecoration is LinearLayoutSpacingItemDecoration) {
            itemDecoration.reset(orientation ?: RecyclerView.VERTICAL, startSpacing, topSpacing, endSpacing, bottomSpacing, innerSpacing).let { if (it) invalidateItemDecorations() }
            found = true
            break
        }
    }
    if (!found) {
        addItemDecoration(LinearLayoutSpacingItemDecoration(orientation ?: RecyclerView.VERTICAL, startSpacing, topSpacing, endSpacing, bottomSpacing, innerSpacing))
    }
}

@BindingAdapter(
    "gridLayout_spanCount",
    "gridLayout_orientation",
    "gridLayout_reverseLayout",
    "gridLayout_startSpacing",
    "gridLayout_topSpacing",
    "gridLayout_endSpacing",
    "gridLayout_bottomSpacing",
    "gridLayout_horizontalInnerSpacing",
    "gridLayout_verticalInnerSpacing",
    requireAll = false
)
fun RecyclerView.setGridLayoutManager(
    @IntRange(from = 1) spanCount: Int,
    @RecyclerView.Orientation orientation: Int?,
    reverseLayout: Boolean,
    @Dimension startSpacing: Float,
    @Dimension topSpacing: Float,
    @Dimension endSpacing: Float,
    @Dimension bottomSpacing: Float,
    @Dimension horizontalInnerSpacing: Float,
    @Dimension verticalInnerSpacing: Float
) {
    // 设置 GridLayoutManager
    layoutManager.apply {
        if (this is GridLayoutManager) {
            setSpanCount(spanCount)
            setOrientation(orientation ?: RecyclerView.VERTICAL)
            setReverseLayout(reverseLayout)
        } else {
            layoutManager = GridLayoutManager(context, spanCount, orientation ?: RecyclerView.VERTICAL, reverseLayout)
        }
    }
    // 设置 GridLayoutSpacingItemDecoration
    var found = false
    for (index in 0 until itemDecorationCount) {
        val itemDecoration = getItemDecorationAt(index)
        if (itemDecoration is GridLayoutSpacingItemDecoration) {
            itemDecoration.reset(spanCount, orientation ?: RecyclerView.VERTICAL, startSpacing, topSpacing, endSpacing, bottomSpacing, horizontalInnerSpacing, verticalInnerSpacing).let { if (it) invalidateItemDecorations() }
            found = true
            break
        }
    }
    if (!found) {
        addItemDecoration(
            GridLayoutSpacingItemDecoration(spanCount, orientation ?: RecyclerView.VERTICAL, startSpacing, topSpacing, endSpacing, bottomSpacing, horizontalInnerSpacing, verticalInnerSpacing)
        )
    }
}
