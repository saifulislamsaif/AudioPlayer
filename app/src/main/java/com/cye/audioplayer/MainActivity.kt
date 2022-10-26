package com.cye.audioplayer

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cye.audioplayer.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        innitializeLayout()
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
        binding.nav.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.navFeedBack->Toast.makeText(baseContext,"FeedBack",Toast.LENGTH_SHORT).show()
                R.id.navSetting->Toast.makeText(baseContext,"Settings",Toast.LENGTH_SHORT).show()
                R.id.navAbout->Toast.makeText(baseContext,"About",Toast.LENGTH_SHORT).show()
                R.id.navExit-> exitProcess(1)
            }
            true
        }
    }


    private fun requestRuntimePermission() :Boolean{
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 13)
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 13){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted",Toast.LENGTH_SHORT).show()
            }
            else
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 13)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    private fun innitializeLayout(){
        setTheme(R.style.MusicPlayer)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toggle = ActionBarDrawerToggle(this,binding.root,R.string.open,R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val musicList = ArrayList<String>()
        musicList.add("First Song")
        musicList.add("2nd Song")
        musicList.add("3rd Song")
        musicList.add("4th Song")
        musicList.add("5th Song")
        binding.musicRV.setHasFixedSize(true)
        binding.musicRV.setItemViewCacheSize(13)
        binding.musicRV.layoutManager= LinearLayoutManager(this)
        musicAdapter =MusicAdapter(this,musicList)
        binding.musicRV.adapter =musicAdapter
        binding.totalSongs.text="Total Songs : "+musicAdapter.itemCount
    }
}