package net.novate.base.widget.recyclerview

import android.graphics.Rect
import android.util.LayoutDirection
import android.view.View
import androidx.annotation.CheckResult
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView

/**
 * 线型间隙装饰器
 */
class LinearLayoutSpacingItemDecoration(
    @RecyclerView.Orientation private var orientation: Int = RecyclerView.VERTICAL,
    @Dimension private var startSpacing: Float = 0f,
    @Dimension private var topSpacing: Float = 0f,
    @Dimension private var endSpacing: Float = 0f,
    @Dimension private var bottomSpacing: Float = 0f,
    @Dimension private var innerSpacing: Float = 0f
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            super.getItemOffsets(outRect, view, parent, state)
        } else {
            val lastPosition = state.itemCount - 1
            if (orientation == RecyclerView.VERTICAL) {
                outRect.left = startSpacing.toInt()
                outRect.right = endSpacing.toInt()
                when (position) {
                    0 -> outRect.top = topSpacing.toInt()
                    lastPosition -> {
                        outRect.top = innerSpacing.toInt()
                        outRect.bottom = bottomSpacing.toInt()
                    }
                    else -> {
                        outRect.top = innerSpacing.toInt()
                    }
                }
            } else {
                outRect.top = topSpacing.toInt()
                outRect.bottom = bottomSpacing.toInt()
                when (position) {
                    0 -> outRect.left = startSpacing.toInt()
                    lastPosition -> {
                        outRect.left = innerSpacing.toInt()
                        outRect.right = endSpacing.toInt()
                    }
                    else -> {
                        outRect.left = innerSpacing.toInt()
                    }
                }
            }
            if (parent.layoutDirection == LayoutDirection.RTL) {
                // 适配从右向左的布局
                outRect.left = outRect.right.also { outRect.right = outRect.left }
            }
        }
    }

    /**
     * 重新设置属性
     *
     * @return true : 有属性修改；false : 无属性修改；检查以重置布局
     */
    @CheckResult
    fun reset(
        @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
        @Dimension startSpacing: Float = 0f,
        @Dimension topSpacing: Float = 0f,
        @Dimension endSpacing: Float = 0f,
        @Dimension bottomSpacing: Float = 0f,
        @Dimension innerSpacing: Float = 0f
    ): Boolean {
        var same = (this.orientation == orientation).also { if (!it) this.orientation = orientation }
        same = (this.startSpacing == startSpacing).also { if (!it) this.startSpacing = startSpacing } && same
        same = (this.topSpacing == topSpacing).also { if (!it) this.topSpacing = topSpacing } && same
        same = (this.endSpacing == endSpacing).also { if (!it) this.endSpacing = endSpacing } && same
        same = (this.bottomSpacing == bottomSpacing).also { if (!it) this.bottomSpacing = bottomSpacing } && same
        same = (this.innerSpacing == innerSpacing).also { if (!it) this.innerSpacing = innerSpacing } && same
        return same
    }
}