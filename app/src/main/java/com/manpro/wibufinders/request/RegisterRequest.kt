package com.manpro.wibufinders.request

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)
