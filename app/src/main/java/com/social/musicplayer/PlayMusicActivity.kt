package com.social.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_play_music.*

class PlayMusicActivity : AppCompatActivity(), View.OnClickListener {
    private var listSongs = mutableListOf<Music>()
    private val mp: MediaPlayer by lazy {
        MediaPlayer()
    }
    val layoutResId: Int
        get() = R.layout.activity_play_music

    fun inIView() {
//     arguments
    }

    override fun onClick(p0: View?) {

    }


}