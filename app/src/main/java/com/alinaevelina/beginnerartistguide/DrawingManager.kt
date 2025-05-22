package com.alinaevelina.beginnerartistguide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class DrawingManager(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("DrawingsPref", Context.MODE_PRIVATE)
    
    fun saveDrawing(bitmap: Bitmap): Boolean {
        return try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)

            val newId = sharedPref.getInt("drawing_count", 0) + 1

            sharedPref.edit().apply {
                putString("drawing_$newId", encodedImage)
                putInt("drawing_count", newId)
            }.apply()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getAllDrawings(): List<Bitmap> {
        val drawings = mutableListOf<Bitmap>()
        val count = sharedPref.getInt("drawing_count", 0)

        for (i in 1..count) {
            sharedPref.getString("drawing_$i", null)?.let { encodedImage ->
                try {
                    val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
                    BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)?.let {
                        drawings.add(it)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return drawings
    }

    fun deleteDrawing(position: Int) {
        val count = sharedPref.getInt("drawing_count", 0)
        if (position < 0 || position >= count) return

        val editor = sharedPref.edit()

        editor.remove("drawing_${position + 1}")

        for (i in position + 2..count) {
            sharedPref.getString("drawing_$i", null)?.let {
                editor.putString("drawing_${i - 1}", it)
            }
            editor.remove("drawing_$i")
        }

        editor.putInt("drawing_count", count - 1)
        editor.apply()
    }

    fun clearAllDrawings() {
        sharedPref.edit().clear().apply()
    }

    fun getDrawingCount(): Int {
        return sharedPref.getInt("drawing_count", 0)
    }

    fun hasDrawings(): Boolean {
        return getDrawingCount() > 0
    }
}