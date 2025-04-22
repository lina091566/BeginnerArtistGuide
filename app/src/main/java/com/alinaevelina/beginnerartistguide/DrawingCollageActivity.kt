package com.alinaevelina.beginnerartistguide

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DrawingCollageActivity : AppCompatActivity() {
    private lateinit var drawingManager: DrawingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_collage)

        drawingManager = DrawingManager(this)
        showAllDrawings()
    }

    private fun showAllDrawings() {
        val drawings = drawingManager.getAllDrawings()
        val container = findViewById<LinearLayout>(R.id.drawing_container)

        if (drawings.isEmpty()) {
            Toast.makeText(this, "У вас пока нет сохранённых рисунков", Toast.LENGTH_SHORT).show()
            return
        }

        for (drawing in drawings) {
            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    resources.getDimensionPixelSize(R.dimen.drawing_height)
                )
                setImageBitmap(drawing)
                scaleType = ImageView.ScaleType.FIT_CENTER
                adjustViewBounds = true
            }

            container.addView(imageView)
        }
    }
}