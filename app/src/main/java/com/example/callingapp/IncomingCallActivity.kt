package com.example.callingapp;
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity




class IncomingCallActivity : AppCompatActivity() {
    private lateinit var telecomManager: TelecomManager

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incoming_call)

        telecomManager = getSystemService(TelecomManager::class.java)

        val number = intent.getStringExtra("incoming_number")
        findViewById<TextView>(R.id.caller_number).text = number

        findViewById<Button>(R.id.answer_button).setOnClickListener {
            telecomManager.acceptRingingCall()
            finish()
        }

        findViewById<Button>(R.id.reject_button).setOnClickListener {
            telecomManager.endCall()
            finish()
        }
    }
}