package ru.softstone.kotime.presentation.customview.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class ChartView(context: Context, attr: AttributeSet?) : View(context, attr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = RectF()
    private var items: List<ChartItem>? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        rect.top = 0f
        rect.bottom = height.toFloat()

        val minuteSize = width.toFloat() / (24 * 60)

        items?.forEach {
            paint.color = it.color
            rect.left = it.start * minuteSize
            rect.right = it.end * minuteSize
            canvas.drawRect(rect, paint)
        }
    }

    fun showChartItems(items: List<ChartItem>) {
        this.items = items
        invalidate()
    }
}