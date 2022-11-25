package com.example.ecommerceappproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.ETemail)
        etPassword = findViewById(R.id.ETpassword)

        // initialising Firebase auth object
        auth = FirebaseAuth.getInstance()

        var loginBtn = findViewById<Button>(R.id.loginBtn)
        loginBtn.setOnClickListener {
            //Login user. Take them for OTP verification
            login()
        }

        var signupBtn = findViewById<Button>(R.id.signupBtn)
        signupBtn.setOnClickListener {
            //an Intent object linking this current activity to the second/next one to be opened
            var registerActivityIntent = Intent(this, RegisterActivity::class.java)

            //Finally, call the loginActivity to be started
            startActivity(registerActivityIntent)
        }
    }

    fun login()
    {
        val email = etEmail.text.toString()
        val pass = etPassword.text.toString()

        //Thread for Login
        var runnable = Runnable{
            // calling signInWithEmailAndPassword(email, pass) function using Firebase auth object
            // On successful response Display a Toast
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this)
            {
                if (it.isSuccessful)
                {
                    //Logic for authentication will happen here. So that if the credentials are true,
                    // the activity for OTP can be opened
                    //An Intent object linking this current activity to the next one to be opened
                    var OTPActivityIntent = Intent(this, otpActivity::class.java)
                    //Call the OTPActivity to be started
                    startActivity(OTPActivityIntent)
                }
                else
                {
                    Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
                }

            }
        }
        var thread = Thread(runnable)
        thread.start()
    }

}