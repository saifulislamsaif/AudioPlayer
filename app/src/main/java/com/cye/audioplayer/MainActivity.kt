package com.cye.audioplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cye.audioplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MusicPlayer)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.shuffleBtn.setOnClickListener {
//            Toast.makeText(this@MainActivity,"Button Clicked",Toast.LENGTH_SHORT).show()
//        }
        binding.shuffleBtn.setOnClickListener{
            val intent = Intent(this,PlayerActivity::class.java)
            startActivity(intent)
        }
        binding.favouriteBtn.setOnClickListener{
            val intent = Intent(this,FavouriteActivity::class.java)
            startActivity(intent)
        }
        binding.playListBtn.setOnClickListener{
            val intent = Intent(this,PlayListActivity::class.java)
            startActivity(intent)
        }
    }
}