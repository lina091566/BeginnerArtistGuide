package com.alinaevelina.beginnerartistguide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alinaevelina.beginnerartistguide.DrawingCanvasActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val learningMaterialsButton = findViewById<Button>(R.id.button_learning_materials)
        val colorCircleButton = findViewById<Button>(R.id.button_color_circle)
        val drawingIdeasButton = findViewById<Button>(R.id.button_drawing_ideas)
        val drawingCanvasButton = findViewById<Button>(R.id.button_drawing_canvas)
        val drawingCollageButton = findViewById<Button>(R.id.button_drawing_collage)

        learningMaterialsButton.setOnClickListener {
            val intent = Intent(this, LearningMaterialsActivity::class.java)
            startActivity(intent)
        }

        colorCircleButton.setOnClickListener {
            val intent = Intent(this, ColorCircleActivity::class.java)
            startActivity(intent)
        }

        drawingIdeasButton.setOnClickListener {
            val intent = Intent(this, DrawingIdeasActivity::class.java)
            startActivity(intent)
        }

        drawingCanvasButton.setOnClickListener {
            val intent = Intent(this, DrawingCanvasActivity::class.java)
            startActivity(intent)
        }

        drawingCollageButton.setOnClickListener {
            val intent = Intent(this, DrawingCollageActivity::class.java)
            startActivity(intent)
        }
    }
}