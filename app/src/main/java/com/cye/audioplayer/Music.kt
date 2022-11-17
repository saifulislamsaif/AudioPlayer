package com.cye.audioplayer

import java.time.Duration

data class Music(val id:String,val title:String,val album:String,val artist:String,val duration: Long = 0,val path:String)