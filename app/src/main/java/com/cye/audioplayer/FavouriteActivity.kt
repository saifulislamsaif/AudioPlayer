package com.cye.audioplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FavouriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MusicPlayer)
        setContentView(R.layout.activity_favourite)
    }
}