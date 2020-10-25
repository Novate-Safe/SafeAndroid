package net.novate.base.widget.recyclerview

import android.graphics.Rect
import android.util.LayoutDirection
import android.view.View
import androidx.annotation.CheckResult
import androidx.annotation.Dimension
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import net.novate.base.kotlin.unsafeLazy
import kotlin.math.roundToInt

/**
 * 网格间隙装饰器
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

    private val horizontalSpacings by unsafeLazy { getVerticalItemHorizontalSpacings() }
    private val verticalSpacings by unsafeLazy { getHorizontalItemVerticalSpacings() }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            super.getItemOffsets(outRect, view, parent, state)
        } else {
            if (orientation == RecyclerView.VERTICAL) {
                getVerticalItemOffsets(outRect, position, state.itemCount)
            } else {
                getHorizontalItemOffsets(outRect, position, state.itemCount)
            }
        }

        if (parent.layoutDirection == LayoutDirection.RTL) {
            // 适配从右向左的布局
            outRect.left = outRect.right.also { outRect.right = outRect.left }
        }
    }

    /**
     * 计算纵向排列时各项的偏移
     *    0 1 2
     *
     * 0  0 1 2
     * 1  3 4 5
     * 2  6 7 8
     * 3  9 . .
     * .  . . .
     */
    private fun getVerticalItemOffsets(outRect: Rect, position: Int, itemCount: Int) {
        val lastPosition: Int = itemCount - 1

        // 最后一行的纵坐标（从 0 开始）
        val lastY: Int = lastPosition / spanCount

        // 当前点的横坐标
        val currentX: Int = position % spanCount
        // 当前点的纵坐标
        val currentY: Int = position / spanCount

        outRect.left = horizontalSpacings[currentX].first
        outRect.right = horizontalSpacings[currentX].second

        if (currentY == 0 && currentY == lastY) {
            // 如果当前点的纵坐标即是 0 也是 lastY，表示当前点即是第一行也是最后一行：上下边距设为各自指定值
            outRect.top = topSpacing.roundToInt()
            outRect.bottom = bottomSpacing.roundToInt()
        } else if (currentY == 0) {
            // 第一行：上边距设为 topSpacing，下边距设为 verticalInnerSpacing
            outRect.top = topSpacing.roundToInt()
            outRect.bottom = verticalInnerSpacing.roundToInt()
        } else if (currentY == lastY) {
            // 最后一行：上边距设为 verticalInnerSpacing，下边距设为 bottomSpacing
            outRect.top = verticalInnerSpacing.roundToInt()
            outRect.bottom = bottomSpacing.roundToInt()
        } else {
            // 既不是第一行也不是最后一行：上下边距均设为 verticalInnerSpacing
            outRect.top = verticalInnerSpacing.roundToInt()
            outRect.bottom = verticalInnerSpacing.roundToInt()
        }
    }

    // 计算纵向排列中每一列的左右间距
    private fun getVerticalItemHorizontalSpacings(): Array<Pair<Int, Int>> {
        // 最后一列的横坐标（从 0 开始）
        val lastX: Int = spanCount - 1

        // 每一项在横向上能够占用的空间（包括左右两边），用于平均横向上的每一项的大小，保证横向的每一项都是一样宽的
        val itemSpacing: Float = (startSpacing + horizontalInnerSpacing * (spanCount - 1) + endSpacing) / spanCount

        val startSpacings: Array<Float> = Array(spanCount) { 0F }
        val endSpacings: Array<Float> = Array(spanCount) { 0F }

        repeat(spanCount) { currentX ->
            if (currentX == 0) {
                // 若是第一列，左边距为 startSpacing，右边距为 itemSpacing 减去它的左边距
                startSpacings[currentX] = startSpacing
                endSpacings[currentX] = itemSpacing - startSpacings[currentX]
            } else {
                // 若不是第一列，左边距为 horizontalInnerSpacing 减去上一列的右边距，右边距为 itemSpacing 减去它的左边距
                startSpacings[currentX] = horizontalInnerSpacing - endSpacings[currentX - 1]
                endSpacings[currentX] = itemSpacing - startSpacings[currentX]
            }
            if (currentX == lastX) {
                // 若是最后一列，补上右边距
                endSpacings[currentX] = endSpacing
            }
        }
        return Array(spanCount) { startSpacings[it].toInt() to endSpacings[it].toInt() }
    }

    /**
     * 计算横向排列时各项的偏移
     *
     *    0 1 2 3 .
     *
     * 0  0 3 6 9 .
     * 1  1 4 7 . .
     * 2  2 5 8 . .
     */
    private fun getHorizontalItemOffsets(outRect: Rect, position: Int, itemCount: Int) {
        val lastPosition = itemCount - 1

        // 最后一列的横坐标（从 0 开始）
        val lastX = lastPosition / spanCount

        // 当前点的横坐标
        val currentX = position / spanCount
        val currentY = position % spanCount

        if (currentX == 0 && currentX == lastX) {
            // 如果当前点的横坐标即是 0 也是 lastX，表示当前点即是第一列也是最后一列：左右边距设为各自指定值
            outRect.left = startSpacing.roundToInt()
            outRect.right = endSpacing.roundToInt()
        } else if (currentX == 0) {
            // 第一列：左边距设为 startSpacing，右边距设为 horizontalInnerSpacing
            outRect.left = startSpacing.roundToInt()
            outRect.right = horizontalInnerSpacing.roundToInt()
        } else if (currentY == 0) {
            // 最后一列：左边距设为 horizontalInnerSpacing，右边距设为 endSpacing
            outRect.left = horizontalInnerSpacing.roundToInt()
            outRect.right = endSpacing.roundToInt()
        } else {
            // 既不是第一列也不是最后一列：左右边距均设为 horizontalInnerSpacing
            outRect.left = horizontalInnerSpacing.roundToInt()
            outRect.right = horizontalInnerSpacing.roundToInt()
        }

        outRect.top = verticalSpacings[currentY].first
        outRect.bottom = verticalSpacings[currentY].second
    }

    // 计算横向排列中每一行的上下间距
    private fun getHorizontalItemVerticalSpacings(): Array<Pair<Int, Int>> {
        // 最后一行的纵坐标（从 0 开始）
        val lastY: Int = spanCount - 1

        // 每一项在纵向上能够占用的空间（包括上下两边），用于平均纵向上的每一项的大小，保证纵向的每一项都是一样高的
        val itemSpacing: Float = (topSpacing + verticalInnerSpacing * (spanCount - 1) + bottomSpacing) / spanCount

        val topSpacings: Array<Float> = Array(spanCount) { 0F }
        val bottomSpacings: Array<Float> = Array(spanCount) { 0F }

        repeat(spanCount) { currentY ->
            if (currentY == 0) {
                // 若是第一列，左边距为 startSpacing，右边距为 itemSpacing 减去它的左边距
                topSpacings[currentY] = topSpacing
                bottomSpacings[currentY] = itemSpacing - topSpacings[currentY]
            } else {
                // 若不是第一列，左边距为 horizontalInnerSpacing 减去上一列的右边距，右边距为 itemSpacing 减去它的左边距
                topSpacings[currentY] = verticalInnerSpacing - bottomSpacings[currentY - 1]
                bottomSpacings[currentY] = itemSpacing - topSpacings[currentY]
            }
            if (currentY == lastY) {
                // 若是最后一列，补上右边距
                bottomSpacings[currentY] = bottomSpacing
            }
        }
        return Array(spanCount) { topSpacings[it].toInt() to bottomSpacings[it].toInt() }
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
        var same = (this.spanCount == spanCount).also { if (!it) this.spanCount = spanCount }
        same = (this.orientation == orientation).also { if (!it) this.orientation = orientation } && same
        same = (this.startSpacing == startSpacing).also { if (!it) this.startSpacing = startSpacing } && same
        same = (this.topSpacing == topSpacing).also { if (!it) this.topSpacing = topSpacing } && same
        same = (this.endSpacing == endSpacing).also { if (!it) this.endSpacing = endSpacing } && same
        same = (this.bottomSpacing == bottomSpacing).also { if (!it) this.bottomSpacing = bottomSpacing } && same
        same = (this.horizontalInnerSpacing == horizontalInnerSpacing).also { if (!it) this.horizontalInnerSpacing = horizontalInnerSpacing } && same
        same = (this.verticalInnerSpacing == verticalInnerSpacing).also { if (!it) this.verticalInnerSpacing = verticalInnerSpacing } && same
        return same
    }
}