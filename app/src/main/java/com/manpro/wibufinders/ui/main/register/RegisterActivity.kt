package com.manpro.wibufinders.ui.main.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manpro.wibufinders.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        auth = FirebaseAuth.getInstance()

        binding.btnBack.setOnClickListener {
            // Kembali ke halaman LoginActivity
            finish()
        }

        binding.btnConfirm.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etRePassword.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()

        // Validasi email dan password
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            binding.etEmail.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Enter a valid email"
            binding.etEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            binding.etPassword.requestFocus()
            return
        }

        if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters long"
            binding.etPassword.requestFocus()
            return
        }

        if (confirmPassword.isEmpty()) {
            binding.etRePassword.error = "Confirm Password is required"
            binding.etRePassword.requestFocus()
            return
        }

        if (password != confirmPassword) {
            binding.etRePassword.error = "Passwords do not match"
            binding.etRePassword.requestFocus()
            return
        }

        // Proses registrasi user menggunakan Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registrasi berhasil
                    // Simpan username ke database Anda di sini
                    Snackbar.make(binding.root, "Registration successful", Snackbar.LENGTH_LONG).show()
                    finish() // Kembali ke halaman LoginActivity setelah registrasi berhasil
                } else {
                    // Registrasi gagal
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Snackbar.make(binding.root, "Email is already registered", Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(binding.root, "Registration failed. Please try again later", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
    }
}
