package com.alinaevelina.beginnerartistguide

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.example.beginnerartistguide.DrawingView

class DrawingCanvasActivity : AppCompatActivity() {
    private lateinit var drawingView: DrawingView
    private lateinit var drawingManager: DrawingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_canvas)

        drawingView = findViewById(R.id.drawing_view)
        drawingManager = DrawingManager(this)

        findViewById<Button>(R.id.btn_color_picker).setOnClickListener {
            showColorPicker()
        }

        findViewById<Button>(R.id.btn_eraser).setOnClickListener {
            drawingView.setColor(Color.WHITE)
        }

        findViewById<Button>(R.id.btn_clear).setOnClickListener {
            drawingView.clearCanvas()
        }

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveDrawing()
        }
    }

    private fun showColorPicker() {
        ColorPickerDialog
            .Builder(this)
            .setTitle("Выберите цвет")
            .setColorShape(ColorShape.CIRCLE)
            .setDefaultColor(drawingView.getCurrentColor())
            .setColorListener { color, colorHex ->
                drawingView.setColor(color)
                Toast.makeText(this, "Выбран цвет: $colorHex", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun saveDrawing() {
        val bitmap = drawingView.getDrawingBitmap()
        if (bitmap != null) {
            if (drawingManager.saveDrawing(bitmap)) {
                Toast.makeText(this, "Рисунок сохранён!", Toast.LENGTH_SHORT).show()

                AlertDialog.Builder(this)
                    .setTitle("Рисунок сохранён")
                    .setMessage("Хотите перейти к коллажу?")
                    .setPositiveButton("Да") { _, _ ->
                        startActivity(Intent(this, DrawingCollageActivity::class.java))
                    }
                    .setNegativeButton("Нет", null)
                    .show()
            } else {
                Toast.makeText(this, "Ошибка при сохранении", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Сначала нарисуйте что-нибудь", Toast.LENGTH_SHORT).show()
        }
    }
}