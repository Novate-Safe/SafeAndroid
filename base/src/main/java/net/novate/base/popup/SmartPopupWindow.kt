package net.novate.base.popup

import android.content.Context
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow

/**
 * 智能弹窗
 */
open class SmartPopupWindow : PopupWindow {

    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(
        contentView: View? = null,
        width: Int = 0,
        height: Int = 0,
        focusable: Boolean = false
    ) : super(contentView, width, height, focusable)

    /**
     * 在指定 [anchor] 视图的指定 [position] 位置，以指定 [gravity] 重力方向偏移指定 [offset] 显示弹窗
     *
     * @param anchor 锚点视图
     * @param position 视图上锚点的位置；指屏幕左边的原点；默认为锚点视图在屏幕上半部分则锚点到视图下边中间，在下半部分则锚点到视图上边界中间
     * @param gravity 重力方向；指屏幕坐标的方向；默认为锚点视图在屏幕上半部分则垂直方向向下，在下半部分则垂直方向向上；水平方向始终向右
     * @param offset 重力方向的偏移量
     */
    fun showOnAnchor(
        anchor: View,
        position: Pair<Horizontal, Vertical> = autoPosition(anchor),
        gravity: Pair<Horizontal, Vertical> = autoGravity(anchor),
        offset: Pair<Int, Int> = 0 to 0
    ) {
        val (horizontalPosition, verticalPosition) = position
        val (horizontalOffset, verticalOffset) = offset

        val window = anchor.getScreenRect()
        val matrix = gravity.getMatrix(window)

        val anchorPoints = anchor.getLocationPointsOnScreen().also { matrix.mapPoints(it) }

        val offsetX = when (horizontalPosition) {
            Horizontal.START -> anchorPoints[0] + horizontalOffset
            Horizontal.CENTER -> (anchorPoints[0] + anchorPoints[2]) / 2 + horizontalOffset
            Horizontal.END -> anchorPoints[2] + horizontalOffset
        }
        val offsetY = when (verticalPosition) {
            Vertical.TOP -> anchorPoints[1] + verticalOffset
            Vertical.CENTER -> (anchorPoints[1] + anchorPoints[3]) / 2 + verticalOffset
            Vertical.BOTTOM -> anchorPoints[3] + verticalOffset
        }
        showAtLocation(anchor, gravity.toGravity(), offsetX.toInt(), offsetY.toInt())
    }

    // 把 Pair<Horizontal, Vertical> 类型的 Gravity 转化为 Int 类型的 Gravity
    private fun Pair<Horizontal, Vertical>.toGravity() = let { (horizontal, vertical) ->
        when (horizontal) {
            Horizontal.START -> Gravity.START
            Horizontal.CENTER -> Gravity.CENTER_HORIZONTAL
            Horizontal.END -> Gravity.END
        } or when (vertical) {
            Vertical.TOP -> Gravity.TOP
            Vertical.CENTER -> Gravity.CENTER_VERTICAL
            Vertical.BOTTOM -> Gravity.BOTTOM
        }
    }

    // 自动判断位置
    private fun autoPosition(anchor: View): Pair<Horizontal, Vertical> {
        val anchorLocation = IntArray(2).also { anchor.getLocationInWindow(it) }
        val window = Rect().also { anchor.getWindowVisibleDisplayFrame(it) }
        val anchorCenterY = anchorLocation[1] + anchor.height / 2
        val windowCenterY = window.centerY()
        return Horizontal.CENTER to if (anchorCenterY > windowCenterY) Vertical.TOP else Vertical.BOTTOM
    }

    // 自动判断重力方向
    private fun autoGravity(anchor: View): Pair<Horizontal, Vertical> {
        val anchorLocation = IntArray(2).also { anchor.getLocationInWindow(it) }
        val window = Rect().also { anchor.getWindowVisibleDisplayFrame(it) }
        val anchorCenterY = anchorLocation[1] + anchor.height / 2
        val windowCenterY = window.centerY()
        return Horizontal.CENTER to if (anchorCenterY > windowCenterY) Vertical.BOTTOM else Vertical.TOP
    }

    /**
     * 计算矩阵
     */
    private fun Pair<Horizontal, Vertical>.getMatrix(window: Rect): Matrix {
        return Matrix().apply {
            val (horizontal, vertical) = this@getMatrix
            when (horizontal) {
                Horizontal.START -> {
                    postTranslate(-window.left.toFloat(), 0f)
                }
                Horizontal.CENTER -> {
                    postTranslate(-(window.left + window.right).toFloat() / 2, 0f)
                }
                Horizontal.END -> {
                    postScale(-1f, 1f)
                    postTranslate(window.right.toFloat(), 0f)
                }
            }
            when (vertical) {
                Vertical.TOP -> {
                    postTranslate(0f, -window.top.toFloat())
                }
                Vertical.CENTER -> {
                    postTranslate(0f, -(window.top + window.bottom).toFloat() / 2)
                }
                Vertical.BOTTOM -> {
                    postScale(1f, -1f)
                    postTranslate(0f, window.bottom.toFloat())
                }
            }
        }
    }

    /**
     * 获取屏幕矩阵
     */
    private fun View.getScreenRect(): Rect {
        return Rect().apply {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point().also { windowManager.defaultDisplay.getRealSize(it) }
            set(0, 0, point.x, point.y)
        }
    }

    /**
     * 获取视图在屏幕中左上角和右下角的坐标
     */
    private fun View.getLocationPointsOnScreen(): FloatArray {
        return IntArray(2).also { getLocationOnScreen(it) }.let { array ->
            floatArrayOf(
                array[0].toFloat(),
                array[1].toFloat(),
                array[0].toFloat() + width,
                array[1].toFloat() + height
            )
        }
    }
}

enum class Horizontal {
    // 左，中，右
    START, CENTER, END
}

enum class Vertical {
    // 上中下
    TOP, CENTER, BOTTOM
}