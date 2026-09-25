package com.example.gureevkurilenko

data class User(
    val id: Int = -1,
    val login: String = "",
    val pass: String = "",
    val fullName: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val avatarUri: String = "",
    val isAdmin: Boolean = false
)