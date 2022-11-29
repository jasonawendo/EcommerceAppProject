package com.example.ecommerceappproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity()
{

    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var etConfPass: EditText
    lateinit var btnRegister: Button

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // XML references
        etEmail = findViewById(R.id.ETemail)
        etPass = findViewById(R.id.ETpassword)
        etConfPass = findViewById(R.id.ETconfirmPassword)
        btnRegister = findViewById(R.id.registerBtn)

        // Initialising auth object
        auth = Firebase.auth

        btnRegister.setOnClickListener {
            //Register user
            register()
        }

        var loginBtn = findViewById<Button>(R.id.loginBtn)
        loginBtn.setOnClickListener {
            //Take the user to login
            startLoginActivity()
        }
    }

    fun startLoginActivity()
    {
        //An Intent object linking this current activity to the second/next one to be opened
        var loginActivityIntent = Intent(this, LoginActivity::class.java)

        //Finally, call the loginActivity to be started
        startActivity(loginActivityIntent)
    }

    fun register()
    {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val confirmPassword = etConfPass.text.toString()

        //Thread for authentication
        var runnable = Runnable{
            // check pass
            if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank())
            {
                Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            }

            else if (pass != confirmPassword)
            {
                Toast.makeText(this, "Password and Confirm Password do not match",
                    Toast.LENGTH_SHORT).show()
            }
            // If all credentials have been populated correctly,
            // We call createUserWithEmailAndPassword
            // using auth object and pass the
            // email and pass in it.
            else if (email.isNotBlank() || pass.isNotBlank() || confirmPassword.isNotBlank())
            {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this)
                {
                    if (it.isSuccessful)
                    {
                        //Take the user to login
                        startLoginActivity()
                    }
                    else
                    {
                        Toast.makeText(this, "Signed Up Failed!", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
        var thread = Thread(runnable)
        thread.start()
    }
}