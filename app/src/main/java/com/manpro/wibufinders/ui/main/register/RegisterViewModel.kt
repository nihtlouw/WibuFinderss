package com.manpro.wibufinders.ui.main.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.manpro.wibufinders.utils.Result

class RegisterViewModel : ViewModel() {

    val resultLiveData = MutableLiveData<Result<Unit>>()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String, confirmPassword: String, username: String) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty()) {
            resultLiveData.value = Result.Error("All fields should be filled!")
            return
        }

        if (!isEmailValid(email) || !isPasswordValid(password) || !isConfirmPasswordValid(password, confirmPassword)) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registrasi berhasil
                    // Simpan username ke database Anda di sini
                    resultLiveData.value = Result.Success(Unit)
                } else {
                    // Registrasi gagal
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        resultLiveData.value = Result.Error("Email is already registered")
                    } else {
                        resultLiveData.value = Result.Error("Registration failed. Please try again later")
                    }
                }
            }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            resultLiveData.value = Result.Error("Invalid email format")
            false
        } else {
            true
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return if (password.isEmpty() || password.length < 8) {
            resultLiveData.value = Result.Error("Password should be at least 8 characters long")
            false
        } else {
            true
        }
    }

    private fun isConfirmPasswordValid(password: String, confirmPassword: String): Boolean {
        return if (confirmPassword.isEmpty() || password != confirmPassword) {
            resultLiveData.value = Result.Error("Passwords do not match")
            false
        } else {
            true
        }
    }
}

