package com.cye.audioplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlin.system.exitProcess


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ApplicationClass.PREVIOUS -> Toast.makeText(
                context,
                "Previous Clicked",
                Toast.LENGTH_SHORT
            ).show()
            ApplicationClass.PLAY -> Toast.makeText(context, "Play Clicked", Toast.LENGTH_SHORT)
                .show()
            ApplicationClass.NEXT -> Toast.makeText(context, "NEXT Clicked", Toast.LENGTH_SHORT)
                .show()
            ApplicationClass.PREVIOUS -> Toast.makeText(
                context,
                "Previous Clicked",
                Toast.LENGTH_SHORT
            ).show()
            ApplicationClass.EXIT -> {
                exitProcess(1)
            }
        }
    }
}
