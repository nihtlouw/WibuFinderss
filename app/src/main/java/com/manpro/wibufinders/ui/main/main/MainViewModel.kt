package com.manpro.wibufinders.ui.main.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.manpro.wibufinders.pref.UserModel
import com.manpro.wibufinders.repository.PuitikaRepository

class MainViewModel(private val repository: PuitikaRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}