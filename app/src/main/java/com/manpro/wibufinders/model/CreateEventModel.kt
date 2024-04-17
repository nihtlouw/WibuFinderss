package com.manpro.wibufinders.model

data class CreateEventModel(
    val nama: String,
    val waktu: String,
    val description: String,
    val jenis: Boolean,
    val harga: String,
    val contact: String,
    val penyelenggara: String,
    val lokasi: String,
    val mulai: String,
    val selesai: String,
    val gambar: String
)