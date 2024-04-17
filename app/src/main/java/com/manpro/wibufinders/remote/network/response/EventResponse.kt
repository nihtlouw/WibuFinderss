package com.manpro.wibufinders.remote.network.response
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class EventResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("status")
	val status: String
) : Parcelable

@Parcelize
data class EventDetail(

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("penyelenggara")
	val penyelenggara: String,

	@field:SerializedName("gambar")
	val gambar: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: String,

	@field:SerializedName("lokasi")
	val lokasi: String,

	@field:SerializedName("contact")
	val contact: String,

	@field:SerializedName("selesai")
	val selesai: String,

	@field:SerializedName("waktu")
	val waktu: String,

	@field:SerializedName("jenis")
	val jenis: Boolean,

	@field:SerializedName("mulai")
	val mulai: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("events")
	val events: List<EventDetail>
) : Parcelable
