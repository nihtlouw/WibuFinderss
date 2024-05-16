package com.manpro.wibufinders.DummyFiles

import android.os.Parcelable
import com.manpro.wibufinders.R // Pastikan untuk mengimpor R dari paket aplikasi Anda
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeSocial(
    val error: Boolean,
    val message: String,
    val data: List<AnimeSocialEventDetail>
) : Parcelable

@Parcelize
data class AnimeSocialEventDetail(
    val id: Int,
    val socialMediaName: String,
    val imageBannerUrl: Int, // Ubah tipe menjadi Int untuk resource ID
    val communityLink: String
) : Parcelable

val SocialEventList = AnimeSocial(
    error = false,
    message = "Success",
    data = listOf(
        AnimeSocialEventDetail(
            id = 1,
            socialMediaName = "Instagram",
            imageBannerUrl = R.drawable.ig_event, // Resource ID dari drawable
            communityLink = "https://www.instagram.com/infojejepangan.id/?hl=id"
        ),
        AnimeSocialEventDetail(
            id = 2,
            socialMediaName = "Facebook",
            imageBannerUrl = R.drawable.fb_event, // Resource ID dari drawable
            communityLink = "https://www.facebook.com/groups/251875943835"
        ),
        AnimeSocialEventDetail(
            id = 3,
            socialMediaName = "Twitter",
            imageBannerUrl = R.drawable.twit_event, // Resource ID dari drawable
            communityLink = "https://twitter.com/japanfest1"
        )
    )
)
