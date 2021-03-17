package com.app.darktodoapp.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.app.darktodoapp.R
import com.app.darktodoapp.helper.percent

private const val DEFAULT_STROKE_WIDTH = 10F
private const val DEFAULT_STROKE_MARGIN = 20F

class PieChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var oldPercentage: Float = 0f
    private var percentage: Float = 10f
    private var percentageAnimation: Float = 0f

    private var strokeWidth = DEFAULT_STROKE_WIDTH
    private var strokeMargin = DEFAULT_STROKE_MARGIN

    private val pieRectF = RectF()
    private val strokeRectF = RectF()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PieChart,
            0, 0
        ).apply {
            this@PieChart.rotation = getFloat(R.styleable.PieChart_android_rotation, -90f)

            strokeWidth = getDimension(R.styleable.PieChart_strokeWidth, DEFAULT_STROKE_WIDTH)
            strokeMargin = getDimension(R.styleable.PieChart_strokeMargin, DEFAULT_STROKE_MARGIN)

            getColor(R.styleable.PieChart_color, 0).run(::setColor)
            getFloat(R.styleable.PieChart_percentage, 10f).run(::setPercentage)
            recycle()
        }

        strokePaint.strokeWidth = strokeWidth
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val pieLeft = strokeWidth + strokeMargin
        val pieTop = strokeWidth + strokeMargin
        val pieRight = w.toFloat() - strokeWidth - strokeMargin
        val pieBottom = w.toFloat() - strokeWidth - strokeMargin

        pieRectF.set(pieLeft, pieTop, pieRight, pieBottom)
        strokeRectF.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawStroke()
        canvas?.drawPieContent()
    }

    private fun startAnimation(from: Float, to: Float) {
        if (isInEditMode.not()) {
            val animDuration = ((to - from) * ANIM_PER_ANGLE).toLong()
            ValueAnimator.ofFloat(0f, 100f).apply {
                interpolator = DecelerateInterpolator()
                duration = animDuration
                addUpdateListener {
                    percentageAnimation = it.animatedValue as Float
                    invalidate()
                }
            }.start()
        }
    }

    private fun Canvas.drawPieContent() {
        val percent = if (percentageAnimation < percentage) percentageAnimation else percentage
        val angleValue = MAX_ANGLE.percent(percent).toFloat()
        drawArc(pieRectF, oldPercentage, angleValue, true, paint)
    }

    private fun Canvas.drawStroke() {
        val cx = width.toFloat() / 2
        val cy = height.toFloat() / 2
        val radius = (width / 2).toFloat() - strokeWidth
        drawCircle(cx, cy, radius, strokePaint)
    }

    fun setColor(color: Int) {
        paint.color = color
        strokePaint.color = color
    }

    fun setPercentage(percentage: Float) {
        this.percentage = percentage
        startAnimation(0f, 360f)
    }

    companion object {
        private const val MAX_ANGLE = 360F
        private const val MAX_ANIM_DURATION = 2500
        private const val ANIM_PER_ANGLE = (MAX_ANGLE / MAX_ANIM_DURATION)
    }
}