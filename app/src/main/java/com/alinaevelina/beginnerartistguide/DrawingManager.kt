package com.alinaevelina.beginnerartistguide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class DrawingManager(private val context: Context) {
    // Используем SharedPreferences для хранения рисунков
    private val sharedPref = context.getSharedPreferences("DrawingsPref", Context.MODE_PRIVATE)

    /**
     * Сохраняет рисунок в формате Base64
     * @param bitmap - рисунок для сохранения
     * @return true если сохранение прошло успешно, false при ошибке
     */
    fun saveDrawing(bitmap: Bitmap): Boolean {
        return try {
            // Конвертируем Bitmap в строку Base64
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)

            // Генерируем новый ID для рисунка
            val newId = sharedPref.getInt("drawing_count", 0) + 1

            // Сохраняем в SharedPreferences
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

    /**
     * Получает все сохраненные рисунки
     * @return список Bitmap с рисунками
     */
    fun getAllDrawings(): List<Bitmap> {
        val drawings = mutableListOf<Bitmap>()
        val count = sharedPref.getInt("drawing_count", 0)

        // Читаем все рисунки из хранилища
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

    /**
     * Удаляет конкретный рисунок по позиции в списке
     * @param position - позиция рисунка для удаления (начиная с 0)
     */
    fun deleteDrawing(position: Int) {
        val count = sharedPref.getInt("drawing_count", 0)
        if (position < 0 || position >= count) return

        val editor = sharedPref.edit()

        // 1. Удаляем выбранный рисунок
        editor.remove("drawing_${position + 1}") // +1 т.к. храним с индекса 1

        // 2. Сдвигаем все последующие рисунки
        for (i in position + 2..count) {
            sharedPref.getString("drawing_$i", null)?.let {
                editor.putString("drawing_${i - 1}", it)
            }
            editor.remove("drawing_$i")
        }

        // 3. Обновляем счетчик
        editor.putInt("drawing_count", count - 1)
        editor.apply()
    }

    /**
     * Полностью очищает все сохраненные рисунки
     */
    fun clearAllDrawings() {
        sharedPref.edit().clear().apply()
    }

    /**
     * Возвращает количество сохраненных рисунков
     * @return количество рисунков
     */
    fun getDrawingCount(): Int {
        return sharedPref.getInt("drawing_count", 0)
    }

    /**
     * Проверяет, есть ли сохраненные рисунки
     * @return true если есть хотя бы один рисунок
     */
    fun hasDrawings(): Boolean {
        return getDrawingCount() > 0
    }
}