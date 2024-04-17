package com.manpro.wibufinders.ui.main.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.manpro.wibufinders.repository.PuitikaRepository

class LoginViewModel(repository: PuitikaRepository) : ViewModel() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val loginResultLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            errorMessageLiveData.postValue("Email dan password harus diisi")
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login berhasil, kirim true ke LiveData
                    loginResultLiveData.postValue(true)
                } else {
                    // Login gagal, kirim pesan kesalahan ke LiveData
                    errorMessageLiveData.postValue("Login gagal. Silakan cek kembali email dan password Anda.")
                }
            }
    }

    fun getCurrentUser(): FirebaseUser? {
        return mAuth.currentUser
    }
}



