package com.example.callingapp.models

import java.util.Date

data class CallLogEntry(
    val id: Long? = null,
    val number: String,
    val name: String? = null,
    val date: Date,
    val duration: Long,
    val type: Int, // 1: Incoming, 2: Outgoing, 3: Missed
    val simType: Int = 1,
    val isRead: Boolean = false
) {
    companion object {
        const val CALL_TYPE_INCOMING = 1
        const val CALL_TYPE_OUTGOING = 2
        const val CALL_TYPE_MISSED = 3
    }

    fun getFormattedDuration(): String {
        val minutes = duration / 60
        val seconds = duration % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun getCallTypeString(): String {
        return when (type) {
            CALL_TYPE_INCOMING -> "Incoming"
            CALL_TYPE_OUTGOING -> "Outgoing"
            CALL_TYPE_MISSED -> "Missed"
            else -> "Unknown"
        }
    }
}