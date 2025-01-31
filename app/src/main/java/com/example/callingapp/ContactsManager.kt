package com.example.callingapp

import android.Manifest
import android.content.Context
import android.provider.ContactsContract
import com.example.callingapp.models.Contact
import com.example.callingapp.models.PermissionHandler

class ContactsManager(private val context: Context) {
    private val permissionHandler = PermissionHandler(context)

    fun getContacts(): Result<List<Contact>> {  // Removed the context parameter
        return try {
            if (!permissionHandler.checkPermission(Manifest.permission.READ_CONTACTS)) {
                return Result.failure(SecurityException("READ_CONTACTS permission not granted"))
            }

            val contacts = mutableListOf<Contact>()
            val cursor = context.contentResolver.query(  // Using the class's context
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )

            cursor?.use {
                val nameColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val idColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)

                while (it.moveToNext()) {
                    contacts.add(
                        Contact(
                            id = it.getLong(idColumn),
                            name = it.getString(nameColumn),
                            phoneNumber = it.getString(numberColumn)
                        )
                    )
                }
            }

            Result.success(contacts.distinctBy { it.phoneNumber })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}