package com.cye.audioplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cye.audioplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    companion object{
        lateinit var musicListPlA:ArrayList<Music>
        var songPosition:Int = 0
        var mediaPlayer:MediaPlayer? = null
    }
    private lateinit var binding:ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MusicPlayer)
        setContentView(R.layout.activity_player)
        songPosition = intent.getIntExtra("index",0)
        when(intent.getStringExtra("class") )
        {
            "MusicAdapter"->{
                musicListPlA = ArrayList()
                musicListPlA.addAll(MainActivity.MusicListMA)
                if (mediaPlayer==null) mediaPlayer = MediaPlayer()
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(musicListPlA[songPosition].path)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
            }
        }
    }
}