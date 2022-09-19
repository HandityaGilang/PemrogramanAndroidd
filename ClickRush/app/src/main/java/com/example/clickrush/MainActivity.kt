package com.example.clickrush

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textview = findViewById<TextView>(R.id.textview)
        val textview2 = findViewById<TextView>(R.id.textview2)
        val btnmasuk = findViewById<Button>(R.id.btnmasuk)

        btnmasuk.setOnClickListener {
            textview.text="Hello 71200537-JB Handitya Gilang W!"
            textview2.text="Selamat belajar pemrograman Android!"

        }
    }
}