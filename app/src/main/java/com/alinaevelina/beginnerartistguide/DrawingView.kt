package com.example.beginnerartistguide

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var currentColor = Color.BLACK // Начинаем с черного цвета
    private var brushSize = 20f // Размер кисти
    private var canvasBitmap: Bitmap? = null
    private var drawCanvas: Canvas? = null

    private val paint = Paint().apply {
        color = currentColor
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = brushSize
        isAntiAlias = true
    }

    private val path = Path()
    private val paths = mutableListOf<Pair<Path, Paint>>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap!!)
    }

    fun setColor(newColor: Int) {
        currentColor = newColor
        paint.color = currentColor
    }

    fun clearCanvas() {
        paths.clear()
        canvasBitmap?.eraseColor(Color.TRANSPARENT)
        invalidate() // Перерисовываем экран
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Рисуем фон
        canvas.drawColor(Color.WHITE)

        // Рисуем сохраненное изображение
        canvasBitmap?.let { canvas.drawBitmap(it, 0f, 0f, paint) }

        // Рисуем все сохраненные пути
        for ((path, paint) in paths) {
            canvas.drawPath(path, paint)
        }

        // Рисуем текущий путь
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                path.moveTo(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                drawCanvas?.drawPath(path, paint)
            }
            MotionEvent.ACTION_UP -> {
                // Сохраняем текущий путь
                val newPath = Path(path)
                val newPaint = Paint(paint)
                paths.add(newPath to newPaint)
                drawCanvas?.drawPath(path, paint)
                path.reset()
            }
        }

        invalidate() // Обновляем экран
        return true
    }

    fun getDrawingBitmap(): Bitmap? {
        // Создаем новую bitmap с текущим рисунком
        val bitmap = canvasBitmap?.copy(Bitmap.Config.ARGB_8888, true) ?: return null
        val canvas = Canvas(bitmap)

        // Рисуем все пути на новой bitmap
        for ((path, paint) in paths) {
            canvas.drawPath(path, paint)
        }

        return bitmap
    }
}