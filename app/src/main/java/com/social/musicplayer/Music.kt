package com.social.musicplayer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Music(
    var title: String? = null,
    var author: String? = null,
    var songUrl: String? = null,
    var albumID: Long? = null,
    var uriImage: Uri? = null,
    var duration: Long? = null

    //val duration: String


) : Parcelable {
    fun coverpicture(path: String?): Bitmap? {
        var mr = MediaMetadataRetriever()
        mr.setDataSource(path)
        val byte1 = mr.embeddedPicture
        mr.release()
        return if (byte1 != null) BitmapFactory.decodeByteArray(
            byte1,
            0,
            byte1.size
        ) else null
    }
}




