package com.example.callingapp;
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            when (state) {
                TelephonyManager.EXTRA_STATE_RINGING -> handleIncomingCall(context, number)
                TelephonyManager.EXTRA_STATE_OFFHOOK -> handleCallAnswered(context)
                TelephonyManager.EXTRA_STATE_IDLE -> handleCallEnded(context)
            }
        }
    }

    private fun handleIncomingCall(context: Context, number: String?) {
        val notification = NotificationCompat.Builder(context, "calls")
            .setContentTitle("Incoming Call")
            .setContentText(number ?: "Unknown Number")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)
    }

    private fun handleCallAnswered(context: Context) {
        // Handle call answered state
    }

    private fun handleCallEnded(context: Context) {
        // Handle call ended state
    }
}