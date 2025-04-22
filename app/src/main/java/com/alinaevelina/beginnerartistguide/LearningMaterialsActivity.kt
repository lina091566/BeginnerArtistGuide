package com.alinaevelina.beginnerartistguide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alinaevelina.beginnerartistguide.R

class LearningMaterialsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning_materials)

        // Здесь можно добавить код для загрузки статей из файла или базы данных
        // Пока что просто отображаем текст из XML
    }
}