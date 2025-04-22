package com.alinaevelina.beginnerartistguide

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beginnerartistguide.DrawingView

class DrawingCanvasActivity : AppCompatActivity() {
    private lateinit var drawingView: DrawingView
    private lateinit var drawingManager: DrawingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_canvas)

        drawingView = findViewById(R.id.drawing_view)
        drawingManager = DrawingManager(this)

        // Назначаем цвета кнопкам
        findViewById<Button>(R.id.btn_red).setOnClickListener {
            drawingView.setColor(Color.RED)
        }

        findViewById<Button>(R.id.btn_blue).setOnClickListener {
            drawingView.setColor(Color.BLUE)
        }

        findViewById<Button>(R.id.btn_green).setOnClickListener {
            drawingView.setColor(Color.GREEN)
        }

        findViewById<Button>(R.id.btn_yellow).setOnClickListener {
            drawingView.setColor(Color.YELLOW)
        }

        // Ластик (белый цвет)
        findViewById<Button>(R.id.btn_eraser).setOnClickListener {
            drawingView.setColor(Color.WHITE)
        }

        // Очистить холст
        findViewById<Button>(R.id.btn_clear).setOnClickListener {
            drawingView.clearCanvas()
        }

        // Кнопка сохранения (добавьте эту кнопку в XML)
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveDrawing()
        }
    }

    private fun saveDrawing() {
        val bitmap = drawingView.getDrawingBitmap()
        if (bitmap != null) {
            if (drawingManager.saveDrawing(bitmap)) {
                Toast.makeText(this, "Рисунок сохранён!", Toast.LENGTH_SHORT).show()

                // Спрашиваем, хочет ли пользователь перейти в коллаж
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