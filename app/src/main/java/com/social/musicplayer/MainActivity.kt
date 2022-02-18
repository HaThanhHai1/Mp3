package com.social.musicplayer


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment()
    }

    fun addFragment() {

        val manager = supportFragmentManager
        val tran = manager.beginTransaction()
        val fr = ListMusicFragment()
        tran.replace(R.id.fragment_activity, fr)
        tran.commit()
    }
}