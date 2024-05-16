package com.manpro.wibufinders.ui.main.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manpro.wibufinders.databinding.ActivityRegisterBinding
import com.manpro.wibufinders.model.UserData
import com.manpro.wibufinders.ui.main.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Firebase Auth
        auth = FirebaseAuth.getInstance()
        // Inisialisasi Database Reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        // Tombol "Back" untuk kembali ke halaman Login
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Tombol "Confirm" untuk mendaftarkan pengguna baru
        binding.btnConfirm.setOnClickListener {
            // Mendapatkan nilai username dan password dari input pengguna
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val repassword = binding.etRePassword.text.toString().trim()


            // Menjalankan proses pendaftaran pengguna
            registerUser(username,email,password,repassword)
        }
    }

    private fun registerUser(
        username: String,
        email: String,
        password: String,
        repassword: String
    ) {
        // Validasi input pengguna
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Membuat pengguna baru di Firebase Authentication
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->


                if (task.isSuccessful) {
                    // Jika pendaftaran berhasil
                    val id = auth.currentUser?.uid ?: ""
                    val userData = UserData(id, username, password)
                    // Menyimpan data pengguna ke Firebase Database
                    databaseReference.child(id).setValue(userData)

                    // Menampilkan pesan sukses dengan Snackbar
                    Snackbar.make(binding.root, "Sign up Successful", Snackbar.LENGTH_SHORT).show()

                    // Mengarahkan pengguna ke halaman Login
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    // Jika pendaftaran gagal, menampilkan pesan kesalahan
                    Toast.makeText(this@RegisterActivity, "Sign up Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
