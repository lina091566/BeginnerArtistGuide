package com.alinaevelina.beginnerartistguide

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins

class DrawingCollageActivity : AppCompatActivity() {
    private lateinit var drawingManager: DrawingManager
    private lateinit var container: LinearLayout
    private lateinit var emptyStateText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_collage)

        drawingManager = DrawingManager(this)
        container = findViewById(R.id.drawing_container)
        emptyStateText = findViewById(R.id.empty_state_text)

        showAllDrawings()
    }

    private fun showAllDrawings() {
        container.removeAllViews()
        val drawings = drawingManager.getAllDrawings()

        if (drawings.isEmpty()) {
            emptyStateText.visibility = View.VISIBLE
            return
        } else {
            emptyStateText.visibility = View.GONE
        }

        for ((index, drawing) in drawings.withIndex()) {
            val drawingContainer = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(16.dpToPx(), 8.dpToPx(), 16.dpToPx(), 16.dpToPx())
                }
                background = createBeautifulBorder()
            }

            val imageContainer = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    resources.getDimensionPixelSize(R.dimen.drawing_height)
                )
                setPadding(8.dpToPx(), 8.dpToPx(), 8.dpToPx(), 8.dpToPx())
            }

            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                setImageBitmap(drawing)
                scaleType = ImageView.ScaleType.FIT_CENTER
                adjustViewBounds = true
            }

            val deleteButton = Button(this).apply {
                text = "УДАЛИТЬ"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = android.view.Gravity.CENTER_HORIZONTAL
                    setMargins(0, 8.dpToPx(), 0, 8.dpToPx())
                }
                setBackgroundColor(Color.parseColor("#FF5252"))
                setTextColor(Color.WHITE)

                setOnClickListener {
                    showDeleteConfirmationDialog(index, drawingContainer)
                }
            }

            imageContainer.addView(imageView)
            drawingContainer.addView(imageContainer)
            drawingContainer.addView(deleteButton)
            container.addView(drawingContainer)
        }
    }

    private fun showDeleteConfirmationDialog(index: Int, container: View) {
        AlertDialog.Builder(this)
            .setTitle("Подтверждение удаления")
            .setMessage("Вы точно хотите удалить этот рисунок?")
            .setPositiveButton("ДА") { _, _ ->
                drawingManager.deleteDrawing(index)
                this.container.removeView(container)
                if (drawingManager.getDrawingCount() == 0) {
                    emptyStateText.visibility = View.VISIBLE
                }
                Toast.makeText(this, "Рисунок удалён", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("НЕТ", null)
            .create()
            .show()
    }

    private fun createBeautifulBorder(): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setStroke(4.dpToPx(), Color.parseColor("#BDBDBD"))
            cornerRadius = 12.dpToPx().toFloat()
            setColor(Color.WHITE)
        }
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}