package com.cye.audioplayer

import android.app.Application
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat

class MusicService : Service() {
    var myBinder = MyBinder()
    var mediaPlayer: MediaPlayer? = null
    private lateinit var mediaSession: MediaSessionCompat
    override fun onBind(intent: Intent?): IBinder {
        mediaSession = MediaSessionCompat(baseContext, "My Music")
        return myBinder
    }

    inner class MyBinder : Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }

    fun showNotification() {
        val notification = NotificationCompat.Builder(baseContext, ApplicationClass.Chanel_ID)
            .setContentTitle(PlayerActivity.musicListPlA[PlayerActivity.songPosition].title)
            .setContentText(PlayerActivity.musicListPlA[PlayerActivity.songPosition].artist)
            .setSmallIcon(R.drawable.playlist_icon)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.music_player_icon_slash_screen
                )
            )
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.previous_icon, "Previous", null)
            .addAction(R.drawable.play_icon, "Play", null)
            .addAction(R.drawable.next_icon, "Next", null)
            .addAction(R.drawable.exit_icon, "Exit", null)
            .build()
        startForeground(13, notification)
    }
}