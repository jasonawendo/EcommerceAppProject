package com.example.semproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.example.ecommerceappproject.R
import com.google.android.material.textfield.TextInputEditText

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        var subTotal_2 = 0

        var orderTextView: TextView = findViewById(R.id.order_textview)
        var address: TextInputEditText = findViewById(R.id.address)
        var phoneNumber: TextInputEditText = findViewById(R.id.phone_number)


        // the radio buttons

        var deliveryChecked: RadioButton = findViewById(R.id.delivery)
        var selfPickChecked: RadioButton = findViewById(R.id.pickup)

        // the order button
        var confirmButton: Button = findViewById(R.id.confirmDetails)

        var orderedItem = intent.getStringExtra("ORDER")
        orderTextView.text = orderedItem

        confirmButton.setOnClickListener {

            if (deliveryChecked.isChecked){


                var  delivery = 150
                subTotal_2 += delivery
//
            }
            if (selfPickChecked.isChecked){


                var  selfpick = 0
                subTotal_2 += selfpick
//
            }
            var subTotal_1 = intent.getIntExtra("SUBTOTAL_1",0)

            var Total = subTotal_1 + subTotal_2

            var intentPaymentActivity = Intent(this,PaymentActivity::class.java)
            intentPaymentActivity.putExtra("TOTAL", Total)

            startActivity(intentPaymentActivity)

        }

    }


}