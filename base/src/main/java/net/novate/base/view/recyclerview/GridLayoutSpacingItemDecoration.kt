package net.novate.base.view.recyclerview

import android.graphics.Point
import android.graphics.Rect
import android.view.View
import androidx.annotation.CheckResult
import androidx.annotation.Dimension
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView

/**
 * 网格间隙装饰器；暂未适配反向布局；注意上下左右的间距不可以一侧设置过分离谱，会导致无法均分 Item
 */
class GridLayoutSpacingItemDecoration(
    @IntRange(from = 1) private var spanCount: Int,
    @RecyclerView.Orientation private var orientation: Int = RecyclerView.VERTICAL,
    @Dimension private var startSpacing: Float = 0f,
    @Dimension private var topSpacing: Float = 0f,
    @Dimension private var endSpacing: Float = 0f,
    @Dimension private var bottomSpacing: Float = 0f,
    @Dimension private var horizontalInnerSpacing: Float = 0f,
    @Dimension private var verticalInnerSpacing: Float = 0f
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            super.getItemOffsets(outRect, view, parent, state)
        } else {
            val isHorizontal = orientation == RecyclerView.HORIZONTAL
            val isVertical = orientation == RecyclerView.VERTICAL

            val lastPoint = (state.itemCount - 1).toPoint(spanCount, orientation)
            // RecyclerView 竖着排，最后一个横坐标是 spanCount - 1；横着排，最后一个横坐标是最后一个元素的横坐标
            val lastX = if (isVertical) spanCount - 1 else lastPoint.x
            // RecyclerView 竖着排，最后一个纵坐标是最后一个元素的纵坐标；横着排，最后一个纵坐标是 spanCount - 1
            val lastY = if (isVertical) lastPoint.y else spanCount - 1
            val positionPoint = position.toPoint(spanCount, orientation)

            // 每一项需要额外扩展的间隙，保证每个 ITEM 均分全部间隙
            val itemExpandedSpacing = if (isVertical) {
                (startSpacing + horizontalInnerSpacing * (spanCount - 1) + endSpacing) / spanCount
            } else {
                (topSpacing + verticalInnerSpacing * (spanCount - 1) + bottomSpacing) / spanCount
            }

            when (positionPoint.x) {
                // 横坐标为 0 即 第一列，设置左边距为 startSpacing
                0 -> {
                    outRect.left = startSpacing.toInt()
                    if (isVertical) {
                        outRect.right = (itemExpandedSpacing - startSpacing).toInt()
                    }
                }
                // 横坐标为 lastX 即最后一列，设置左边距为 horizontalInnerSpacing，右边距为 endSpacing
                lastX -> {
                    if (isVertical) {
                        outRect.left = (itemExpandedSpacing - endSpacing).toInt()
                    } else {
                        outRect.left = horizontalInnerSpacing.toInt()
                    }
                    outRect.right = endSpacing.toInt()
                }
                // 其他情况设置左边距为 horizontalInnerSpacing
                else -> {
                    if (isVertical) {
                        outRect.left = (itemExpandedSpacing / 2).toInt()
                        outRect.right = (itemExpandedSpacing / 2).toInt()
                    } else {
                        outRect.left = horizontalInnerSpacing.toInt()
                    }
                }
            }
            when (positionPoint.y) {
                0 -> {
                    outRect.top = topSpacing.toInt()
                    if (isHorizontal) {
                        outRect.bottom = (itemExpandedSpacing - topSpacing).toInt()
                    }
                }
                lastY -> {
                    if (isHorizontal) {
                        outRect.top = (itemExpandedSpacing - bottomSpacing).toInt()
                    } else {
                        outRect.top = verticalInnerSpacing.toInt()
                    }
                    outRect.bottom = bottomSpacing.toInt()
                }
                else -> {
                    if (isHorizontal) {
                        outRect.top = (itemExpandedSpacing / 2).toInt()
                        outRect.bottom = (itemExpandedSpacing / 2).toInt()
                    } else {
                        outRect.top = verticalInnerSpacing.toInt()
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
        @IntRange(from = 1) spanCount: Int,
        @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
        @Dimension startSpacing: Float = 0f,
        @Dimension topSpacing: Float = 0f,
        @Dimension endSpacing: Float = 0f,
        @Dimension bottomSpacing: Float = 0f,
        @Dimension horizontalInnerSpacing: Float = 0f,
        @Dimension verticalInnerSpacing: Float = 0f
    ): Boolean {
        return if (this == GridLayoutSpacingItemDecoration(
                spanCount,
                orientation,
                startSpacing,
                topSpacing,
                endSpacing,
                bottomSpacing,
                horizontalInnerSpacing,
                verticalInnerSpacing
            )
        ) {
            false
        } else {
            this.spanCount = spanCount
            this.orientation = orientation
            this.startSpacing = startSpacing
            this.topSpacing = topSpacing
            this.endSpacing = endSpacing
            this.bottomSpacing = bottomSpacing
            this.horizontalInnerSpacing = horizontalInnerSpacing
            this.verticalInnerSpacing = verticalInnerSpacing
            true
        }
    }

    /**
     * 把 Int 转换为以 spanCount 为跨度的坐标
     *
     * @param spanCount 跨度
     * @param orientation 无限延伸的方向
     *
     * 横着排
     * 0 3 6 9 .
     * 1 4 7 . .
     * 2 5 8 . .
     *
     * 竖着排
     * 0 1 2
     * 3 4 5
     * 6 7 8
     * 9 . .
     * . . .
     *
     * @return Point(X,Y) X：横向坐标 Y：纵向坐标；X,Y 从 0 开始
     */
    private fun Int.toPoint(spanCount: Int, @RecyclerView.Orientation orientation: Int): Point {
        return if (orientation == RecyclerView.VERTICAL) {
            Point(this % spanCount, this / spanCount)
        } else {
            Point(this / spanCount, this % spanCount)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GridLayoutSpacingItemDecoration

        if (spanCount != other.spanCount) return false
        if (orientation != other.orientation) return false
        if (startSpacing != other.startSpacing) return false
        if (topSpacing != other.topSpacing) return false
        if (endSpacing != other.endSpacing) return false
        if (bottomSpacing != other.bottomSpacing) return false
        if (horizontalInnerSpacing != other.horizontalInnerSpacing) return false
        if (verticalInnerSpacing != other.verticalInnerSpacing) return false

        return true
    }

    override fun hashCode(): Int {
        var result = spanCount
        result = 31 * result + orientation
        result = 31 * result + startSpacing.hashCode()
        result = 31 * result + topSpacing.hashCode()
        result = 31 * result + endSpacing.hashCode()
        result = 31 * result + bottomSpacing.hashCode()
        result = 31 * result + horizontalInnerSpacing.hashCode()
        result = 31 * result + verticalInnerSpacing.hashCode()
        return result
    }
}