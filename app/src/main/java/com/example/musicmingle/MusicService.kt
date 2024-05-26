package com.example.musicmingle

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.net.toUri

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        val uriString = intent?.getStringExtra(EXTRA_URL)
        when(action){
            ACTION_PLAY->{
                if(uriString != null){
                    playMusic(uriString)
                }
            }
            ACTION_PAUSE -> pauseMusic()
            ACTION_STOP -> stopMusic()
        }
        return START_STICKY
    }

    private fun playMusic(uriString: String) {
        stopMusic()
        mediaPlayer = MediaPlayer.create(this,uriString.toUri())
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            stopSelf()
        }
    }
    private fun stopMusic(){
        mediaPlayer?.let {
            if(it.isPlaying){
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null

    }
    private fun pauseMusic() {
        mediaPlayer?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }

    companion object Actions{
        const val ACTION_PLAY = "action_play"
        const val EXTRA_URL = "extra_url"
        const val ACTION_STOP ="action_stop"
        const val ACTION_PAUSE="action_pause"
    }
}
