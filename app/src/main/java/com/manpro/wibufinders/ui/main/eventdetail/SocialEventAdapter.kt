package com.manpro.wibufinders.ui.main.eventdetail


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manpro.wibufinders.DummyFiles.AnimeSocialEventDetail
import com.manpro.wibufinders.R
import com.squareup.picasso.Picasso

class SocialEventAdapter (private val socialEventList: List<AnimeSocialEventDetail>) :
    RecyclerView.Adapter<SocialEventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.social_event_list_model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = socialEventList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return socialEventList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivSocialImage: ImageView = itemView.findViewById(R.id.iv_socialimage)
        private val tvSocialMedia: TextView = itemView.findViewById(R.id.tv_socialmedia)
        private val ivArrow: ImageView = itemView.findViewById(R.id.iv_arrow)

        fun bind(item: AnimeSocialEventDetail) {
            Picasso.get().load(item.imageBannerUrl).into(ivSocialImage)
            tvSocialMedia.text = item.socialMediaName

            // Menambahkan onClickListener pada anak panah
            ivArrow.setOnClickListener {
                // Aksi ketika anak panah diklik
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.communityLink))
                itemView.context.startActivity(intent)
            }
        }
    }
}