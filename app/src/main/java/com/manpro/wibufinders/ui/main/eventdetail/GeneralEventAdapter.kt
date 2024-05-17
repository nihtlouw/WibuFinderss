package com.manpro.wibufinders.ui.main.eventdetail

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manpro.wibufinders.DummyFiles.AnimeFestEventDetail
import com.manpro.wibufinders.R
import org.threeten.bp.LocalDate
import java.util.Locale
import org.threeten.bp.format.DateTimeFormatter
import kotlin.math.*

class GeneralEventAdapter(var eventList: MutableList<AnimeFestEventDetail>) :
    RecyclerView.Adapter<GeneralEventAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var originalEventList: List<AnimeFestEventDetail> = mutableListOf()
    private var userLocation: Location? = null

    init {
        originalEventList = eventList.toList() // Copy the original event list
        prepareEventList(FILTER_BY_DATE) // Or pass any other filter type you want
    }

    fun prepareEventList(filterType: Int = 0) {
        when (filterType) {
            FILTER_BY_LOCATION -> filterEventsByLocation()
            FILTER_BY_DATE -> filterEventsByDate()
            else -> eventList = originalEventList.toMutableList()
        }
        notifyDataSetChanged()
    }
    private fun filterEventsByLocation() {
        userLocation?.let { userLoc ->
            eventList = eventList.filter { event ->
                event.longitude != null && userLoc.latitude != null && userLoc.longitude != null && event.latitude != 0.0 && event.longitude != 0.0 && userLoc.latitude != 0.0 && userLoc.longitude != 0.0 && calculateDistance(userLoc.latitude, userLoc.longitude, event.latitude, event.longitude) <= 10.0
            }.toMutableList()
        }
    }

    private fun filterEventsByDate() {
        val currentDate = LocalDate.now()
        eventList = eventList.filter { event ->
            event.eventDate >= currentDate
        }.toMutableList()
    }
    fun filterEventsByDate(date: LocalDate) {
        eventList = originalEventList.filter { event ->
            event.eventDate >= date
        }.toMutableList()
        notifyDataSetChanged()
    }

    fun filterEventsByLocation(location: Location?) {
        location?.let { userLoc ->
            eventList = originalEventList.filter { event ->
                event.latitude != null && event.longitude != null &&
                        userLoc.latitude != null && userLoc.longitude != null &&
                        event.latitude != 0.0 && event.longitude != 0.0 &&
                        userLoc.latitude != 0.0 && userLoc.longitude != 0.0 &&
                        calculateDistance(userLoc.latitude, userLoc.longitude, event.latitude, event.longitude) <= 10.0
            }.toMutableList()
        }
        notifyDataSetChanged()
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
    companion object {
        const val FILTER_BY_LOCATION = 1
        const val FILTER_BY_DATE = 2
    }


    // Sisanya dari kelas adapter...

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.general_event_list_model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]

        // Load image using Glide if image URL is available
        if (!event.imageUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context).load(event.imageUrl).into(holder.ivEvent)
        }

        // Set event name text
        holder.tvEventName.text = event.eventName
        // Set event date text
        val dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        holder.tvEventDate.text = event.eventDate.format(dateFormat)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onClick(it, event)
        }
    }



    override fun getItemCount(): Int {
        return eventList.size
    }

    // Fungsi untuk mengatur ulang data acara

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivEvent: ImageView = itemView.findViewById(R.id.iv_eventimage)
        val tvEventName: TextView = itemView.findViewById(R.id.tv_event_name)
        val tvEventDate: TextView = itemView.findViewById(R.id.tv_generalevent)

    }

    fun setData(newEventList: List<AnimeFestEventDetail>) {
        val diffCallback = DiffUtilCallback(eventList, newEventList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        eventList.clear()
        eventList.addAll(newEventList)
        sortEventListByIdDescending()
        diffResult.dispatchUpdatesTo(this)
    }
    private fun sortEventListByIdDescending() {
        eventList.sortByDescending { it.id }
    }

    // Interface to handle item click
    interface OnItemClickListener {
        fun onClick(clickedView: View, event: AnimeFestEventDetail)
    }

    private class DiffUtilCallback(
        private val oldList: List<AnimeFestEventDetail>,
        private val newList: List<AnimeFestEventDetail>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

