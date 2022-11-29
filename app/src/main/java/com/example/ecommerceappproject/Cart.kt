package com.example.ecommerceappproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Cart : AppCompatActivity() {
    lateinit var recyclerbtn : Button
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        recyclerbtn = findViewById(R.id.recyclerviewbtn)

        recyclerbtn.setOnClickListener {

            var i = Intent(this, productList::class.java)
            startActivity(i)
            finish()


        }

    }
}