package net.novate.base.view.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.annotation.CheckResult
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView

/**
 * 线型间隙装饰器；暂未适配反向布局
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
        return if (this == LinearLayoutSpacingItemDecoration(
                orientation,
                startSpacing,
                topSpacing,
                endSpacing,
                bottomSpacing,
                innerSpacing
            )
        ) {
            false
        } else {
            this.orientation = orientation
            this.startSpacing = startSpacing
            this.topSpacing = topSpacing
            this.endSpacing = endSpacing
            this.bottomSpacing = bottomSpacing
            this.innerSpacing = innerSpacing
            true
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LinearLayoutSpacingItemDecoration

        if (orientation != other.orientation) return false
        if (startSpacing != other.startSpacing) return false
        if (topSpacing != other.topSpacing) return false
        if (endSpacing != other.endSpacing) return false
        if (bottomSpacing != other.bottomSpacing) return false
        if (innerSpacing != other.innerSpacing) return false

        return true
    }

    override fun hashCode(): Int {
        var result = orientation
        result = 31 * result + startSpacing.hashCode()
        result = 31 * result + topSpacing.hashCode()
        result = 31 * result + endSpacing.hashCode()
        result = 31 * result + bottomSpacing.hashCode()
        result = 31 * result + innerSpacing.hashCode()
        return result
    }


}