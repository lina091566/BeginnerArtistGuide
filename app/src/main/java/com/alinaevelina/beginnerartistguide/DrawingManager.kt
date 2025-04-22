package com.alinaevelina.beginnerartistguide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class DrawingManager(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("DrawingsPref", Context.MODE_PRIVATE)

    // Сохраняем рисунок
    fun saveDrawing(bitmap: Bitmap): Boolean {
        try {
            // Конвертируем Bitmap в строку Base64
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)

            // Получаем текущее количество рисунков
            val count = sharedPref.getInt("drawing_count", 0) + 1

            // Сохраняем новый рисунок
            sharedPref.edit()
                .putString("drawing_$count", encodedImage)
                .putInt("drawing_count", count)
                .apply()

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    // Получаем все сохраненные рисунки
    fun getAllDrawings(): List<Bitmap> {
        val drawings = mutableListOf<Bitmap>()
        val count = sharedPref.getInt("drawing_count", 0)

        for (i in 1..count) {
            val encodedImage = sharedPref.getString("drawing_$i", null)
            if (encodedImage != null) {
                val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                drawings.add(bitmap)
            }
        }

        return drawings
    }

    // Очищаем все сохраненные рисунки
    fun clearAllDrawings() {
        sharedPref.edit().clear().apply()
    }
}