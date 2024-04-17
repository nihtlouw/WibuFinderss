package com.manpro.wibufinders.ui.main.eventdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manpro.wibufinders.model.CreateEventModel
import com.manpro.wibufinders.remote.network.response.CreateEventResponse
import com.manpro.wibufinders.repository.PuitikaRepository
import com.manpro.wibufinders.request.CreateEventRequest


class AddEventFormViewModel(private val repository: PuitikaRepository) : ViewModel() {
    fun createEvent(createEventModel: CreateEventModel): LiveData<Result<CreateEventResponse>> {
        val resultLiveData = MutableLiveData<Result<CreateEventResponse>>()

        val createEventRequest = CreateEventRequest(
            nama = createEventModel.nama,
            waktu = createEventModel.waktu,
            description = createEventModel.description,
            jenis = createEventModel.jenis,
            harga = createEventModel.harga,
            contact = createEventModel.contact,
            penyelenggara = createEventModel.penyelenggara,
            lokasi = createEventModel.lokasi,
            mulai = createEventModel.mulai,
            selesai = createEventModel.selesai,
            gambar = createEventModel.gambar
        )

        repository.createEvent(createEventRequest).observeForever { result ->
            resultLiveData.value = result
        }

        return resultLiveData
    }
}