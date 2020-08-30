package net.novate.base.binding

import androidx.annotation.Dimension
import androidx.annotation.IntRange
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.novate.base.base.BaseBindingAdapter
import net.novate.base.view.recyclerview.GridLayoutSpacingItemDecoration
import net.novate.base.view.recyclerview.LinearLayoutSpacingItemDecoration

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