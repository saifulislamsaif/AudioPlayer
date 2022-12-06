package com.cye.audioplayer

import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cye.audioplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() ,ServiceConnection{
    companion object {
        lateinit var musicListPlA: ArrayList<Music>
        var songPosition: Int = 0
        var repeat: Boolean = false
        var isPlaying: Boolean = false
        var musicService:MusicService? = null
    }

    @SuppressLint("StaticFieldLeak")
    lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MusicPlayer)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent = Intent(this,MusicService::class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        startService(intent)
        initializeLayout()
        binding.playPauseBtnPA.setOnClickListener {
            if (isPlaying) pauseMusic()
            else
                playMusic()
        }
        binding.previousBtnPA.setOnClickListener { prevNextSong(increment = false) }
        binding.nextBtnPA.setOnClickListener { prevNextSong(increment = true) }
    }

    private fun setLayout() {
        Glide.with(applicationContext)
            .load(musicListPlA[songPosition].artUri)
            .apply(
                RequestOptions().placeholder(R.drawable.music_player_icon_slash_screen).centerCrop()
            )
            .into(binding.songImgPA)
        binding.songNamePA.text = musicListPlA[songPosition].title
    }

    private fun createMediaPlayer() {
        try {
            if (musicService!!.mediaPlayer == null)musicService!!.mediaPlayer= MediaPlayer()
            musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(musicListPlA[songPosition].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
            isPlaying = true
            binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
        } catch (e: java.lang.Exception) {
            return
        }

    }

    private fun initializeLayout() {
        songPosition = intent.getIntExtra("index", 0)
        when (intent.getStringExtra("class")) {
            "MusicAdapter" -> {
                musicListPlA = ArrayList()
                musicListPlA.addAll(MainActivity.MusicListMA)
                setLayout()

            }
            "MainActivity" -> {
                musicListPlA = ArrayList()
                musicListPlA.addAll(MainActivity.MusicListMA)
                musicListPlA.shuffle()
                setLayout()

            }
        }
    }

    private fun playMusic() {
        binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
        isPlaying = true
        musicService!!.mediaPlayer!!.start()
    }

    private fun pauseMusic() {
        binding.playPauseBtnPA.setIconResource(R.drawable.play_icon)
        isPlaying = false
        musicService!!.mediaPlayer!!.pause()
    }

    fun setSongPosition(increment: Boolean) {
        if (!PlayerActivity.repeat) {
            if (increment) {
                if (PlayerActivity.musicListPlA.size - 1 == PlayerActivity.songPosition)
                    PlayerActivity.songPosition = 0
                else ++PlayerActivity.songPosition
            } else {
                if (0 == PlayerActivity.songPosition)
                    PlayerActivity.songPosition = PlayerActivity.musicListPlA.size - 1
                else --PlayerActivity.songPosition
            }
        }
    }

    private fun prevNextSong(increment: Boolean) {
        if (increment) {
            setSongPosition(increment = true)
            setLayout()
            createMediaPlayer()
        } else {
            setSongPosition(increment = false)
            setLayout()
            createMediaPlayer()
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        var binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.showNotification()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
     musicService = null
    }
}