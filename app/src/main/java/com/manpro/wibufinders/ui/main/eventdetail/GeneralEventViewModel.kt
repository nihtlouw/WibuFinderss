package com.manpro.wibufinders.ui.main.eventdetail

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manpro.wibufinders.DummyFiles.AnimeFestEventDetail
import org.threeten.bp.LocalDate

class GeneralEventViewModel : ViewModel() {

    private val _eventList = MutableLiveData<List<AnimeFestEventDetail>>()
    val eventList: LiveData<List<AnimeFestEventDetail>> = _eventList

    // Fungsi untuk mengupdate eventList dengan hasil pencarian
    fun setSearchResults(searchResults: List<AnimeFestEventDetail>) {
        _eventList.value = searchResults
    }

    fun filterEventsByDate(date: LocalDate) {
        val currentEventList = _eventList.value
        if (currentEventList != null) {
            val filteredEvents = currentEventList.filter { event ->
                event.eventDate >= date
            }
            _eventList.value = filteredEvents
        }
    }

    fun filterEventsByLocation(location: Location?) {
        val currentEventList = _eventList.value
        if (currentEventList != null && location != null) {
            val filteredEvents = currentEventList.filter { event ->
                event.latitude != null && event.longitude != null &&
                        location.latitude != null && location.longitude != null &&
                        event.latitude != 0.0 && event.longitude != 0.0 &&
                        location.latitude != 0.0 && location.longitude != 0.0 &&
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

