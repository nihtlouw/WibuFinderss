package com.manpro.wibufinders.ui.main.eventdetail

import androidx.lifecycle.ViewModel
import com.manpro.wibufinders.repository.PuitikaRepository

class EventViewModel(private val repository: PuitikaRepository) : ViewModel() {
    fun getEvents() = repository.getEvents()
}