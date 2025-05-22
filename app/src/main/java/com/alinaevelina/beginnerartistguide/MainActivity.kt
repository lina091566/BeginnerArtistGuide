package com.alinaevelina.beginnerartistguide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardLessons = findViewById<CardView>(R.id.card_lessons)
        val cardPalette = findViewById<CardView>(R.id.card_palette)
        val cardIdeas = findViewById<CardView>(R.id.card_ideas)
        val cardCanvas = findViewById<CardView>(R.id.card_canvas)
        val cardCollage = findViewById<CardView>(R.id.card_collage)

        cardLessons.setOnClickListener {
            startActivity(Intent(this, LearningMaterialsActivity::class.java))
        }

        cardPalette.setOnClickListener {
            startActivity(Intent(this, ColorCircleActivity::class.java))
        }

        cardIdeas.setOnClickListener {
            startActivity(Intent(this, DrawingIdeasActivity::class.java))
        }

        cardCanvas.setOnClickListener {
            startActivity(Intent(this, DrawingCanvasActivity::class.java))
        }

        cardCollage.setOnClickListener {
            startActivity(Intent(this, DrawingCollageActivity::class.java))
        }
    }
}