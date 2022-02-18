package com.social.musicplayer

import AdapterMusic
import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list_music.*


class ListMusicFragment : BaseFragment(), View.OnClickListener, AdapterMusic.IMusic {
    private lateinit var mLayoutManager: LinearLayoutManager
    private var listSongs = mutableListOf<Music>()
    private var adapter: AdapterMusic? = null
    private val mp: MediaPlayer by lazy {
        MediaPlayer()
    }

    companion object {
        const val MODEL_KEY = "MODEL_KEY"
    }

    override val layoutResId: Int
        get() = R.layout.fragment_list_music

    override fun inIView() {
        checkPermission()
    }

    override fun onClick(p0: View?) {

    }

    private fun checkPermission() {
        val READ_EXTERNAL_PERMISSION =
            ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                BluetoothGattCharacteristic.PERMISSION_READ
            )
        } else loadMusicFromLocal()
    }

    override fun onItemClick(position: Int) {
//        val path = listSongs[position].songUrl
//        val activityPlay: PlayMusicActivity = PlayMusicActivity()
//        val bundle: Bundle = Bundle()
//        bundle.putParcelable(MODEL_KEY, listSongs[position])
//        path?.let {
//            if (mp.isPlaying) {
//                mp.stop()
//                mp.reset()
//            }
//            val uri: Uri = Uri.parse(it)
//            mp.setDataSource(context!!, uri)
//            mp.prepare()
//            mp.start()
//        }
        var intent = Intent(context, PlayActivity::class.java)
        // start your next activity
        startActivity(intent)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            BluetoothGattCharacteristic.PERMISSION_READ -> {
                if (grantResults.isNotEmpty() && permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(
                            context,
                            "Please allow storage permission",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        loadMusicFromLocal()
                    }
                }
            }
        }
    }


    private fun loadMusicFromLocal() {
        val allSongUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor? = context!!.contentResolver.query(allSongUri, null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val songUrl: String =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor: String? =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName: String =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    val albumId: Long =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                    val uriImage = Uri.parse("content://media/external/audio/albumart/$albumId")

                    val duration: Long =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    listSongs.add(Music(songName, songAuthor, songUrl, albumId, uriImage, duration))
                    //Log.d("list", listSongs.toString())

                } while (cursor.moveToNext())

            }
        }
        cursor?.close()
//        mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//        rcvView.layoutManager = mLayoutManager
        adapter = AdapterMusic(listSongs, this)
        rcvView.adapter = adapter
    }


}