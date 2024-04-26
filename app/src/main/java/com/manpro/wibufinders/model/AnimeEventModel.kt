package com.manpro.wibufinders.model

import android.os.Parcel
import android.os.Parcelable

data class AnimeEventModel(
    val eventName: String?,
    val eventDate: String?,
    val location: String?,
    val description: String?,
    val imageUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eventName)
        parcel.writeString(eventDate)
        parcel.writeString(location)
        parcel.writeString(description)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimeEventModel> {
        override fun createFromParcel(parcel: Parcel): AnimeEventModel {
            return AnimeEventModel(parcel)
        }

        override fun newArray(size: Int): Array<AnimeEventModel?> {
            return arrayOfNulls(size)
        }
    }
}

