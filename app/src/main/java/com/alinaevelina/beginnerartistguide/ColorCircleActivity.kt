package com.alinaevelina.beginnerartistguide

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class ColorCircleActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_circle)

        gridLayout = findViewById(R.id.gridLayout)
        generateRandomColorCircles()

        val generateButton: Button = findViewById(R.id.buttonGenerate)
        generateButton.setOnClickListener {
            gridLayout.removeAllViews()
            generateRandomColorCircles()
            Toast.makeText(this, "Новые цвета созданы!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateRandomColorCircles() {
        for (i in 0 until 6) {
            val colorHex = generateRandomColorHex()
            val colorCircle = createColorCircle(colorHex)
            colorCircle.setOnClickListener {
                Toast.makeText(this, "Цвет: $colorHex", Toast.LENGTH_SHORT).show()
            }
            gridLayout.addView(colorCircle)
        }
    }

    private fun generateRandomColorHex(): String {
        val randomColor = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        return String.format("#%06X", (0xFFFFFF and randomColor))
    }

    private fun createColorCircle(colorHex: String): ImageView {
        return ImageView(this).apply {
            setBackgroundColor(Color.parseColor(colorHex))
            layoutParams = GridLayout.LayoutParams().apply {
                width = 200
                height = 200
                setMargins(8, 8, 8, 8)
            }
            setImageResource(android.R.color.transparent)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }
}