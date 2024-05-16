package com.manpro.wibufinders.ui.main.eventdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.manpro.wibufinders.DummyFiles.AnimeFestEventDetail
import com.manpro.wibufinders.R
import com.manpro.wibufinders.databinding.ActivityEventDetailBinding
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class EventAnimeDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var event: AnimeFestEventDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil objek AnimeFestEventDetail dari intent
        event = intent.getParcelableExtra<AnimeFestEventDetail>("event")!!

        // Memeriksa apakah objek event tidak null
        if (event != null) {
            setupView(event)
        }

        // Inisialisasi MapView
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // Tambahkan marker dengan koordinat dari AnimeFestEventDetail
        val location = LatLng(event.latitude, event.longitude)
        googleMap.addMarker(MarkerOptions().position(location).title("Event Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))

        // Tambahkan listener untuk mendeteksi klik pada marker
        googleMap.setOnMarkerClickListener { marker ->
            // Buat Intent untuk membuka Google Maps dengan koordinat yang ditentukan
            val intentUri = Uri.parse("geo:${event.latitude},${event.longitude}?q=${Uri.encode("(${event.latitude},${event.longitude})")}")
            val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            // Pastikan ada aplikasi yang bisa menangani intent
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                // Handle jika tidak ada aplikasi Google Maps di perangkat
                Toast.makeText(this, "Google Maps app not found", Toast.LENGTH_SHORT).show()
            }
            true // Kembalikan true untuk menunjukkan bahwa event ini sudah ditangani
        }
    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun setupView(event: AnimeFestEventDetail) {
        // Memuat gambar menggunakan Glide
        Glide.with(this)
            .load(event.imageUrl)
            .into(binding.ivEventimage)

        // Mengatur teks dan data lainnya sesuai dengan objek event
        binding.apply {
            tvEventname.text = event.eventName
            // Konversi tanggal ke format yang sesuai
            val formattedDate = event.eventDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
            tvEventdate.text = formattedDate
            tvLocation.text = event.location
            tvDetailEvent.text = event.description
            tvSponsornamedetail.text = event.sponsorName
            tvContactpersondetail.text = event.contactPersonWhatsApp
            tvEventforumlinkdetail.text = event.eventForumLink
            // Anda dapat menambahkan logika tambahan sesuai kebutuhan
        }
    }
}




