package com.manpro.wibufinders.ui.main.eventdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manpro.wibufinders.R
import com.manpro.wibufinders.model.AnimeEventModel

class EventAdapter(private val context: Context, private val eventList: List<AnimeEventModel>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]

        // Mengambil URL gambar dari Firebase Cloud Firestore
        val imageUrl = event.imageUrl

        // Memuat gambar menggunakan Glide jika URL gambar tersedia
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context).load(imageUrl).into(holder.ivEvent)
        }

        // Setel teks nama acara
        holder.tvEventName.text = event.eventName

        // Menangani klik pada item
        holder.itemView.setOnClickListener {
            onItemClickListener?.onClick(it, event)
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivEvent: ImageView = itemView.findViewById(R.id.iv_event_item_image)
        val tvEventName: TextView = itemView.findViewById(R.id.tv_event_name)
    }

    // Interface untuk menangani klik pada item
    interface OnItemClickListener {
        fun onClick(clickedView: View, event: AnimeEventModel)
    }
}


