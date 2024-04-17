package com.manpro.wibufinders.model

data class RegisterModel(
    val username: String,
    val email: String,
    val password: String,
    val repassword : String
)
