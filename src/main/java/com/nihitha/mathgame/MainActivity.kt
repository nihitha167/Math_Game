package com.nihitha.mathgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var Addition:Button
    lateinit var Subtraction:Button
    lateinit var Multiplication:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Addition = findViewById(R.id.buttonAdd)
        Subtraction = findViewById(R.id.buttonSub)
        Multiplication = findViewById(R.id.buttonMul)

        Addition.setOnClickListener {
            var intent = Intent(this@MainActivity,GameActivity::class.java)
            intent.putExtra("operation","addition")
            startActivity(intent)
        }

        Subtraction.setOnClickListener {
            var intent = Intent(this@MainActivity,GameActivity::class.java)
            intent.putExtra("operation","subtraction")
            startActivity(intent)
        }

        Multiplication.setOnClickListener {
            var intent = Intent(this@MainActivity,GameActivity::class.java)
            intent.putExtra("operation","multiplication")
            startActivity(intent)
        }
    }
}