package net.novate.base.binding

import androidx.annotation.Dimension
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
fun <D, B : ViewDataBinding> RecyclerView.setData(adapter: BaseBindingAdapter<D, B>, data: List<*>? = null) {
    adapter.data = data as? List<D> ?: listOf()
    if (this.adapter !== adapter) {
        this.adapter = adapter
    }
}

@BindingAdapter(
    value = [
        "linearLayout_orientation",
        "linearLayout_reverseLayout",
        "linearLayout_startSpacing",
        "linearLayout_topSpacing",
        "linearLayout_endSpacing",
        "linearLayout_bottomSpacing",
        "linearLayout_innerSpacing"
    ],
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
    if (layoutManager == null || layoutManager !is LinearLayoutManager) {
        layoutManager = LinearLayoutManager(
            context,
            orientation ?: RecyclerView.VERTICAL,
            reverseLayout
        )
    } else {
        (layoutManager as LinearLayoutManager).apply {
            setOrientation(orientation ?: RecyclerView.VERTICAL)
            setReverseLayout(reverseLayout)
        }
    }

    // TODO: 2020/8/27 可以优化
    // 设置 LinearLayoutSpacingItemDecoration
    var found = false
    for (index in 0 until itemDecorationCount) {
        val itemDecoration = getItemDecorationAt(index)
        if (itemDecoration is LinearLayoutSpacingItemDecoration) {
            itemDecoration.reset(
                orientation ?: RecyclerView.VERTICAL,
                startSpacing, topSpacing, endSpacing, bottomSpacing, innerSpacing
            ).takeIf { it }?.let { invalidateItemDecorations() }
            found = true
            break
        }
    }
    if (!found) {
        addItemDecoration(
            LinearLayoutSpacingItemDecoration(
                orientation ?: RecyclerView.VERTICAL,
                startSpacing, topSpacing, endSpacing, bottomSpacing, innerSpacing
            )
        )
    }
}

@BindingAdapter(
    value = [
        "gridLayout_spanCount",
        "gridLayout_orientation",
        "gridLayout_reverseLayout",
        "gridLayout_startSpacing",
        "gridLayout_topSpacing",
        "gridLayout_endSpacing",
        "gridLayout_bottomSpacing",
        "gridLayout_horizontalInnerSpacing",
        "gridLayout_verticalInnerSpacing"
    ], requireAll = false
)
fun RecyclerView.setGridLayoutManager(
    spanCount: Int,
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
    if (layoutManager == null || layoutManager !is GridLayoutManager) {
        layoutManager = GridLayoutManager(
            context,
            spanCount,
            orientation ?: RecyclerView.VERTICAL,
            reverseLayout
        )
    } else {
        (layoutManager as GridLayoutManager).apply {
            setSpanCount(spanCount)
            setOrientation(orientation ?: RecyclerView.VERTICAL)
            setReverseLayout(reverseLayout)
        }
    }
    // 设置 GridLayoutSpacingItemDecoration
    var found = false
    for (index in 0 until itemDecorationCount) {
        val itemDecoration = getItemDecorationAt(index)
        if (itemDecoration is GridLayoutSpacingItemDecoration) {
            itemDecoration.reset(
                spanCount,
                orientation ?: RecyclerView.VERTICAL,
                startSpacing,
                topSpacing,
                endSpacing,
                bottomSpacing,
                horizontalInnerSpacing,
                verticalInnerSpacing
            ).takeIf { it }?.let { invalidateItemDecorations() }
            found = true
            break
        }
    }
    if (!found) {
        addItemDecoration(
            GridLayoutSpacingItemDecoration(
                spanCount,
                orientation ?: RecyclerView.VERTICAL,
                startSpacing,
                topSpacing,
                endSpacing,
                bottomSpacing,
                horizontalInnerSpacing,
                verticalInnerSpacing
            )
        )
    }
}