package com.example.callingapp

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.provider.CallLog
import com.example.callingapp.models.CallLogEntry
import com.example.callingapp.models.PermissionHandler
import java.util.Date

class CallLogManager(private val context: Context) {

    fun addCallToLog(number: String, duration: Long, type: Int): Result<Unit> {
        return try {

            if (!PermissionHandler(context).checkPermission(Manifest.permission.WRITE_CALL_LOG)) {
                return Result.failure(SecurityException("WRITE_CALL_LOG permission not granted"))
            }

            val values = ContentValues().apply {
                put(CallLog.Calls.NUMBER, number)
                put(CallLog.Calls.DATE, Date().time)
                put(CallLog.Calls.DURATION, duration)
                put(CallLog.Calls.TYPE, type)
                put(CallLog.Calls.NEW, 1)
            }

            context.contentResolver.insert(CallLog.Calls.CONTENT_URI, values)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCallLogs(): Result<List<CallLogEntry>> {
        return try {

            if (!PermissionHandler(context).checkPermission(Manifest.permission.READ_CALL_LOG)) {
                return Result.failure(SecurityException("READ_CALL_LOG permission not granted"))
            }

            val callLogs = mutableListOf<CallLogEntry>()
            val cursor = context.contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                "${CallLog.Calls.DATE} DESC"
            )

            cursor?.use {
                val numberColumn = it.getColumnIndex(CallLog.Calls.NUMBER)
                val dateColumn = it.getColumnIndex(CallLog.Calls.DATE)
                val durationColumn = it.getColumnIndex(CallLog.Calls.DURATION)
                val typeColumn = it.getColumnIndex(CallLog.Calls.TYPE)
                val nameColumn = it.getColumnIndex(CallLog.Calls.CACHED_NAME)

                while (it.moveToNext()) {
                    callLogs.add(
                        CallLogEntry(
                            number = it.getString(numberColumn),
                            name = it.getString(nameColumn),
                            date = Date(it.getLong(dateColumn)),
                            duration = it.getLong(durationColumn),
                            type = it.getInt(typeColumn)
                        )
                    )
                }
            }

            Result.success(callLogs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
