package com.manpro.wibufinders.ui.main.eventdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manpro.wibufinders.DummyFiles.AnimeFestEventDetail
import com.manpro.wibufinders.R
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class PopularEventAdapter(private var eventList: List<AnimeFestEventDetail>) :
    RecyclerView.Adapter<PopularEventAdapter.ViewHolder>() {

    private val originalEventList: List<AnimeFestEventDetail> = eventList.toList()
    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_event_list_model, parent, false)
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

        // Format event date
        val formattedDate = event.eventDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        holder.tvEventDate.text = formattedDate
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivEvent: ImageView = itemView.findViewById(R.id.iv_eventpops)
        val tvEventName: TextView = itemView.findViewById(R.id.tv_title_eventpopular)
        val tvEventDate: TextView = itemView.findViewById(R.id.tv_date_eventpopular)

        init {
            // Menambahkan listener klik ke setiap item ViewHolder
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.onClick(itemView, eventList[position])
                }
            }
        }
    }

    // Interface to handle item click
    interface OnItemClickListener {
        fun onClick(clickedView: View, event: AnimeFestEventDetail)
    }

    // Metode untuk mengembalikan data ke kondisi aslinya
    fun resetData() {
        eventList = originalEventList.toList()
        notifyDataSetChanged()
    }
    // Method to set event list with only big events (bigevent = 1)
    fun setBigEvents(events: List<AnimeFestEventDetail>) {
        eventList = events.filter { it.bigevent == 1 }
        notifyDataSetChanged()
    }
}
