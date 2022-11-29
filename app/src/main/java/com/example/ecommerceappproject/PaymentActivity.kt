package com.example.semproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.ecommerceappproject.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        var showReceipt : TextView = findViewById(R.id.showReceipt)
        var payBtn : FloatingActionButton = findViewById(R.id.btnPayment)


        var Total = intent.getIntExtra("TOTAL",0)
        var showTotal = findViewById<TextView>(R.id.Total)
        showTotal.text = Total.toString()

        payBtn.setOnClickListener {


            val intent =
                applicationContext.packageManager.getLaunchIntentForPackage("com.android.stk")
            if (intent != null){
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext, "NO SIM CARD FOUND", Toast.LENGTH_LONG).show()
            }


        }
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this,"Cart abandoned! Return to complete Payment :)", Toast.LENGTH_LONG).show()
    }
}