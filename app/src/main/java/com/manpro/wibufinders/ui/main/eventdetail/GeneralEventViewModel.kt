package com.manpro.wibufinders.ui.main.eventdetail

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manpro.wibufinders.DummyFiles.AnimeFestEventDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class GeneralEventViewModel : ViewModel() {

    private val _eventList = MutableLiveData<List<AnimeFestEventDetail>>()
    val eventList: LiveData<List<AnimeFestEventDetail>> = _eventList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Fungsi untuk mengupdate eventList dengan hasil pencarian
    fun setSearchResults(searchResults: List<AnimeFestEventDetail>) {
        _eventList.value = searchResults
    }

    // Fungsi untuk memulai pencarian
    fun performSearch(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            delay(2000) // Simulasi delay 2 detik
            val searchResults = searchData(query)
            _eventList.value = searchResults
            _isLoading.value = false
        }
    }

    private fun searchData(query: String): List<AnimeFestEventDetail> {
        val currentEventList = _eventList.value ?: emptyList()
        return currentEventList.filter { event ->
            event.eventName.contains(query, ignoreCase = true)
        }.also { searchResults ->
            if (searchResults.isEmpty()) {
                // Tampilkan pesan event tidak ditemukan jika hasil pencarian kosong
                // Perlu dipanggil di thread utama
                viewModelScope.launch {
                    // Gunakan postValue karena tidak di thread utama
                    _eventList.postValue(currentEventList)
                }
            }
        }
    }

    fun filterEventsByDate(date: LocalDate) {
        val currentEventList = _eventList.value ?: return
        val filteredEvents = currentEventList.filter { event ->
            event.eventDate >= date
        }
        _eventList.value = filteredEvents
    }

    fun filterEventsByLocation(location: Location?) {
        val currentEventList = _eventList.value ?: return
        if (location != null) {
            val filteredEvents = currentEventList.filter { event ->
                event.latitude != null && event.longitude != null &&
                        event.latitude != 0.0 && event.longitude != 0.0 &&
                        calculateDistance(location.latitude, location.longitude, event.latitude, event.longitude) <= 10.0
            }
            _eventList.value = filteredEvents
        }
    }

    // Function to calculate distance between two coordinates
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371 // Radius of the earth in km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R * c // Distance in km
    }
}
