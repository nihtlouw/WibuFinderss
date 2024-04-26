package com.manpro.wibufinders.ui.main.eventdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.manpro.wibufinders.databinding.ActivityEventDetailBinding
import com.manpro.wibufinders.model.AnimeEventModel

class EventAnimeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil objek EventDetail dari intent
        val event = intent.getParcelableExtra<AnimeEventModel>("EXTRA_EVENT")

        // Memeriksa apakah objek event tidak null
        if (event != null) {
            setupView(event)
        }
    }

    private fun setupView(event: AnimeEventModel) {
        // Memuat gambar menggunakan Glide
        Glide.with(this)
            .load(event.imageUrl)
            .into(binding.zoomableImageView)

        // Mengatur teks dan data lainnya sesuai dengan objek event
        binding.apply {
            tvEventname.text = event.eventName
            tvEventdate.text = event.eventDate
            tvLocation.text = event.location
            tvDetailEvent.text = event.description
            // Anda dapat menambahkan logika tambahan sesuai kebutuhan
        }
    }
}
