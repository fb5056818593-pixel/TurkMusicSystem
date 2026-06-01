package com.turk.musicsystem

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.audiofx.Equalizer
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.turk.musicsystem.services.MusicPlayerService
import com.turk.musicsystem.utils.EqualizerManager

class MusicPlayerActivity : AppCompatActivity() {
    private var musicService: MusicPlayerService? = null
    private var isBound = false
    private var equalizer: Equalizer? = null
    private lateinit var equalizerManager: EqualizerManager

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicPlayerService.MusicBinder
            musicService = binder.getService()
            isBound = true
            initializeEqualizer()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)
        bindMusicService()
        setupControls()
        setupEqualizer()
    }

    private fun bindMusicService() {
        bindService(Intent(this, MusicPlayerService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun setupControls() {
        val playPauseBtn = findViewById<Button>(R.id.btnPlayPause)
        playPauseBtn.setOnClickListener {
            if (musicService?.isPlaying() == true) {
                musicService?.pause()
                playPauseBtn.text = "Oynat"
            } else {
                musicService?.play()
                playPauseBtn.text = "Duraklat"
            }
        }

        findViewById<Button>(R.id.btnPrevious).setOnClickListener {
            musicService?.previousTrack()
            updateTrackInfo()
        }

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            musicService?.nextTrack()
            updateTrackInfo()
        }

        findViewById<SeekBar>(R.id.sbProgress).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) musicService?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupEqualizer() {
        equalizerManager = EqualizerManager(this)

        findViewById<Button>(R.id.btnEqualizerNormal).setOnClickListener {
            equalizerManager.setNormalPreset()
        }

        findViewById<Button>(R.id.btnBassBoost).setOnClickListener {
            equalizerManager.setBassBoost()
        }

        findViewById<Button>(R.id.btnTrebleBoost).setOnClickListener {
            equalizerManager.setTrebleBoost()
        }

        findViewById<Button>(R.id.btnLive).setOnClickListener {
            equalizerManager.setLivePreset()
        }
    }

    private fun initializeEqualizer() {
        try {
            if (musicService != null) {
                val audioSessionId = musicService!!.getAudioSessionId()
                if (audioSessionId != 0) {
                    equalizer = Equalizer(0, audioSessionId)
                    equalizer?.enabled = true
                    equalizerManager.setEqualizer(equalizer!!)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Ekolayzer başlatılamadı", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTrackInfo() {
        musicService?.let {
            findViewById<TextView>(R.id.tvSongName).text = it.getCurrentTrackName()
            findViewById<TextView>(R.id.tvArtist).text = it.getCurrentArtist()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        equalizer?.release()
        if (isBound) unbindService(serviceConnection)
    }
}