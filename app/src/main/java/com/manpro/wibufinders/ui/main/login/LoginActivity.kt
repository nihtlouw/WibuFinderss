    package com.manpro.wibufinders.ui.main.login

    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.TextView
    import android.widget.Toast
    import androidx.lifecycle.ViewModelProvider
    import com.google.firebase.auth.FirebaseAuth
    import com.manpro.wibufinders.R
    import com.manpro.wibufinders.ui.main.main.MainActivity
    import com.manpro.wibufinders.ui.main.register.RegisterActivity

    class LoginActivity : AppCompatActivity() {

        private lateinit var viewModel: LoginViewModel

        private lateinit var emailEditText: EditText
        private lateinit var passwordEditText: EditText
        private lateinit var loginButton: Button
        private lateinit var registerTextView: TextView
        private lateinit var mAuth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            mAuth = FirebaseAuth.getInstance()
            viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

            emailEditText = findViewById(R.id.et_username)
            passwordEditText = findViewById(R.id.et_password)
            loginButton = findViewById(R.id.btn_login)
            registerTextView = findViewById(R.id.tv_register)

            loginButton.setOnClickListener {
                loginUser()
            }

            registerTextView.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }

        private fun loginUser() {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
                return
            }

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login berhasil
                        viewModel.loginResultLiveData.postValue(true)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        // Gagal login
                        viewModel.errorMessageLiveData.postValue("Gagal login. Silakan cek kembali email dan password Anda.")
                    }
                }
        }
    }
