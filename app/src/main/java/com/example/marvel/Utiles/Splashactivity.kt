package com.example.marvel.Utiles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marvel.Principales.MainActivity
import com.example.marvel.R


class splashactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashactivity)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}