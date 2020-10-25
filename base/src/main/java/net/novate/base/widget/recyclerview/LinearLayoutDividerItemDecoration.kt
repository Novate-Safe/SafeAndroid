package net.novate.base.widget.recyclerview

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.CheckResult
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

/**
 * 线型分割线装饰器
 *
 * TODO：适配从右向左布局
 */
class LinearLayoutDividerItemDecoration(
    @RecyclerView.Orientation private var orientation: Int = RecyclerView.VERTICAL,
    var divider: Drawable? = null,
    var showLastDivider: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        // 若不显示最后一条分割线，则跳过最后一项
        if (divider == null || position == RecyclerView.NO_POSITION || (!showLastDivider && position == state.itemCount - 1)) {
            return super.getItemOffsets(outRect, view, parent, state)
        }

        if (orientation == RecyclerView.VERTICAL) {
            outRect.set(0, 0, 0, divider?.intrinsicHeight ?: 0)
        } else {
            outRect.set(0, 0, divider?.intrinsicWidth ?: 0, 0)
        }

        /* 暂不支持
        if (parent.layoutDirection == LayoutDirection.RTL) {
            // 适配从右向左布局
            outRect.left = outRect.right.also { outRect.right = outRect.left }
        }*/
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (divider == null || parent.layoutManager == null) {
            return
        }

        if (orientation == RecyclerView.VERTICAL) {
            drawVertical(c, parent, state)
        } else {
            drawHorizontal(c, parent, state)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        canvas.save()
        val left: Int
        val right: Int
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
        } else {
            left = 0
            right = parent.width
        }

        val bounds = Rect()
        // 最后一个需要绘制的索引
        val lastDrawingIndex = state.itemCount - if (showLastDivider) 1 else 2
        repeat(parent.childCount) { index ->
            val child = parent.getChildAt(index)
            // 不绘制最后一个 View
            if (parent.getChildAdapterPosition(child) <= lastDrawingIndex) {
                parent.getDecoratedBoundsWithMargins(child, bounds)
                val bottom = bounds.bottom + child.translationY.roundToInt()
                val top = bottom - (divider?.intrinsicHeight ?: 0)
                divider?.setBounds(left, top, right, bottom)
                divider?.draw(canvas)
            }
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
        } else {
            top = 0
            bottom = parent.height
        }

        val bounds = Rect()
        // 最后一个需要绘制的索引
        val lastDrawingIndex = state.itemCount - if (showLastDivider) 1 else 2
        repeat(parent.childCount) { index ->
            val child = parent.getChildAt(index)
            if (parent.getChildAdapterPosition(child) <= lastDrawingIndex) {
                parent.getDecoratedBoundsWithMargins(child, bounds)
                val right = bounds.right + child.translationX.roundToInt()
                val left = bottom - (divider?.intrinsicWidth ?: 0)
                divider?.setBounds(left, top, right, bottom)
                divider?.draw(canvas)
            }
        }
        canvas.restore()
    }

    /**
     * 重新设置属性
     *
     * @return true : 有属性修改；false : 无属性修改；检查以重置布局
     */
    @CheckResult
    fun reset(
        @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
        divider: Drawable?,
        showLastDivider: Boolean = false
    ): Boolean {
        var same = (this.orientation == orientation).also { if (!it) this.orientation = orientation }
        same = (this.divider === divider).also { if (!it) this.divider = divider } && same
        same = (this.showLastDivider == showLastDivider).also { if (!it) this.showLastDivider = showLastDivider } && same
        return same
    }
}