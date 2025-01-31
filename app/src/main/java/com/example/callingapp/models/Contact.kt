package com.example.callingapp.models

data class Contact(
    val id: Long? = null,
    val name: String,
    val phoneNumber: String,
    val photoUri: String? = null,
    val email: String? = null,
    val address: String? = null,
    val isFavorite: Boolean = false
)