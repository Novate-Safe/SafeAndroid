package net.novate.base.widget.flexlayout

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import net.novate.base.R
import kotlin.math.max
import kotlin.math.min

/**
 * 弹性布局，设置目标的最小宽高、最大宽高和目标宽高，计算出比例不变，满足最小宽高和最大宽高条件的宽高
 *
 * 目标宽高皆在最小宽高和最大宽高范围内，则取目标宽高
 * 目标宽高任意一边小于其最小值，则进行最小程度地放大，使其满最小宽高和最大宽高的条件
 * 目标宽高任意一边大于其最大值，则进行最小程度地缩小，使其满最小宽高和最大宽高的条件
 */
class FlexLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    var minWidth: Int = 0
        set(value) {
            field = value
            requestLayout()
        }
    var minHeight: Int = 0
        set(value) {
            field = value
            requestLayout()
        }
    var maxWidth: Int = 0
        set(value) {
            field = value
            requestLayout()
        }
    var maxHeight: Int = 0
        set(value) {
            field = value
            requestLayout()
        }
    var targetWidth: Int = 0
        set(value) {
            field = value
            requestLayout()
        }
    var targetHeight: Int = 0
        set(value) {
            field = value
            requestLayout()
        }

    init {
        attrs?.let {
            with(context.obtainStyledAttributes(it, R.styleable.FlexLayout, defStyleAttr, defStyleRes)) {
                minWidth = getDimensionPixelSize(R.styleable.FlexLayout_flex_minWidth, 0)
                minHeight = getDimensionPixelSize(R.styleable.FlexLayout_flex_minHeight, 0)
                maxWidth = getDimensionPixelSize(R.styleable.FlexLayout_flex_maxWidth, 0)
                maxHeight = getDimensionPixelSize(R.styleable.FlexLayout_flex_maxHeight, 0)
                targetWidth = getDimensionPixelSize(R.styleable.FlexLayout_flex_targetWidth, 0)
                targetHeight = getDimensionPixelSize(R.styleable.FlexLayout_flex_targetHeight, 0)
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (targetWidth != 0 && targetHeight != 0) {
            with(flex(minWidth, minHeight, maxWidth, maxHeight, targetWidth, targetHeight)) {
                super.onMeasure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
                )
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    private fun flex(minWidth: Int, minHeight: Int, maxWidth: Int, maxHeight: Int, targetWidth: Int, targetHeight: Int): Flat {
        var width = targetWidth
        var height = targetHeight

        if (minWidth == 0 || minHeight == 0 || maxWidth == 0 || maxHeight == 0) {
            // 任意一个值为零都不处理，直接返回目标宽高
            return Flat(width, height)
        }
        if (minWidth > maxWidth || minHeight > maxHeight) {
            // 最小值大于最大值，抛出异常
            throw IllegalArgumentException("minWidth > maxWidth || minHeight > maxHeight")
        }

        val minWidthScale = targetWidth.toFloat() / minWidth
        val maxWidthScale = targetWidth.toFloat() / maxWidth
        val minHeightScale = targetHeight.toFloat() / minHeight
        val maxHeightScale = targetHeight.toFloat() / maxHeight

        when {
            targetWidth < minWidth || targetHeight < minHeight -> {
                // 要放大的比例
                with(min(minWidthScale, minHeightScale)) {
                    width = min((targetWidth / this).toInt(), maxWidth)
                    height = min((targetHeight / this).toInt(), maxHeight)
                }
            }
            targetWidth > maxWidth || targetHeight > maxHeight -> {
                // 要缩小的比例
                with(max(maxWidthScale, maxHeightScale)) {
                    width = max((targetWidth / this).toInt(), minWidth)
                    height = max((targetHeight / this).toInt(), minHeight)
                }
            }
            else -> {
                width = targetWidth
                height = targetHeight
            }
        }
        return Flat(width, height)
    }

    /**
     * 平面，有宽高两个属性
     */
    private class Flat(val width: Int, val height: Int)
}