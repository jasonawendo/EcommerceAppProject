package com.example.ecommerceappproject

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


class otpActivity : AppCompatActivity() {

    private val SMS_CONSENT_REQUEST = 1 // Permission code to access SMS feature
    private val smsVerification = object: BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent)
        {
            if(SmsRetriever.SMS_RETRIEVED_ACTION == intent.action)
            {
                val extras = intent.extras
                val smsRetrieveStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

                when(smsRetrieveStatus.statusCode)
                {
                    CommonStatusCodes.SUCCESS ->
                    {
                        //Get Consent Intent
                        val consentIntent =  extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        try
                        {
                            //Start activity to show consent dialog to a user, activity must be selected in
                            //5 minutes, otherwise another TIMEOUT intent will be received
                            startActivityForResult(consentIntent, SMS_CONSENT_REQUEST)
                        }
                        catch (e: Exception)
                        {
                            Log.e("SMSERROR", e.printStackTrace().toString())
                        }
                    }

                    CommonStatusCodes.TIMEOUT ->
                    {
                        //Timeout occurred, handle the error
                        //Consider a Snackbar with retry option here
                        Toast.makeText(applicationContext, "You took too long to give consent", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    //Declaring XML views
    lateinit var otpEditText: EditText
    lateinit var btnVerify: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        //Initializing XML Views
        otpEditText = findViewById(R.id.etOtpNumber)

        //Register the Broadcast Receiver by indicating which Broadcast event is expected
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsVerification, intentFilter, SmsRetriever.SEND_PERMISSION, null)
        //Call method for autoretriving SMS-declared after this method
        initAutoRefill()
    }

    //Method for Auto-retrivieing SMS Code and filling EditText
    private fun initAutoRefill()
    {
        SmsRetriever.getClient(this)
            .startSmsUserConsent(null)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful)
                {
                    Log.d(otpActivity::class.simpleName, "listening..")
                }
                else
                {
                    Log.d(otpActivity::class.simpleName, "listening..")
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        //Initializing XML btnVerify
        btnVerify = findViewById(R.id.btnVerify)

                super.onActivityResult(requestCode, resultCode, data)
                when(requestCode)
                {
                    SMS_CONSENT_REQUEST ->
                    {
                        if(resultCode == Activity.RESULT_OK && data!=null)
                        {
                            //Get SMS Message Content
                            val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                            //Extract the numbers only
                            val otpCode = message?.filter { it.isDigit() } ?: "" //Return an empty string if no numbers are present
                            //Fill the OTP field
                            otpEditText.setText(otpCode)
                            //Set the EditText cursor to the end of the text
                            otpEditText.setSelection(otpCode.length)
                            //Validating the OTP- normally this should be done by sending OTP to server for validation

                            //This validation can occur after the user has clicked a button to verify the OTP code
                            btnVerify.setOnClickListener{
                                if(otpCode == "123456")
                                {
                                    //Implement Logic for going to the HomeActivity here
                                    Toast.makeText(applicationContext, "Code is correct, welcome to the app!", Toast.LENGTH_LONG).show()
                                }
                                else
                                {
                                    Toast.makeText(applicationContext, "Code is NOT correct. You cannot login to this account!", Toast.LENGTH_LONG).show()
                                    //Implement logic for resending and re-accepting the OTP here
                                    //Consider looking for logic for when a button is pressed, a message is sent- so that the OTP is sent
                                    //automatically.
                                }
                            }

                        }
                    }
                    else ->
                    {
                        //Consent Denied. User should be able to type OTP manually
                        Log.d(otpActivity::class.simpleName, "Permission Denied")
                    }
                }
    }
}