package com.manpro.wibufinders.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manpro.wibufinders.repository.PuitikaRepository
import com.manpro.wibufinders.ui.main.eventdetail.AddEventFormViewModel
import com.manpro.wibufinders.ui.main.eventdetail.EventViewModel
import com.manpro.wibufinders.ui.main.login.LoginViewModel
import com.manpro.wibufinders.ui.main.main.MainViewModel
import com.manpro.wibufinders.ui.main.register.RegisterViewModel

class ViewModelFactory(private val repository: PuitikaRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(AddEventFormViewModel::class.java)) {
            return AddEventFormViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}