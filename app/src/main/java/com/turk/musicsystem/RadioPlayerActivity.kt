package com.turk.musicsystem

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.turk.musicsystem.services.RadioPlayerService

class RadioPlayerActivity : AppCompatActivity() {
    private var radioService: RadioPlayerService? = null
    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as RadioPlayerService.RadioBinder
            radioService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_player)
        bindRadioService()
        setupControls()
    }

    private fun bindRadioService() {
        bindService(Intent(this, RadioPlayerService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun setupControls() {
        val playPauseBtn = findViewById<Button>(R.id.btnPlayPause)
        playPauseBtn.setOnClickListener {
            if (radioService?.isPlaying() == true) {
                radioService?.pause()
                playPauseBtn.text = "Oynat"
            } else {
                radioService?.play()
                playPauseBtn.text = "Duraklat"
            }
        }

        findViewById<Button>(R.id.btnPrevStation).setOnClickListener {
            radioService?.previousStation()
            updateStationInfo()
        }

        findViewById<Button>(R.id.btnNextStation).setOnClickListener {
            radioService?.nextStation()
            updateStationInfo()
        }

        findViewById<SeekBar>(R.id.sbVolume).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) radioService?.setVolume(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateStationInfo() {
        radioService?.let {
            findViewById<TextView>(R.id.tvRadioName).text = it.getCurrentStationName()
            findViewById<TextView>(R.id.tvFrequency).text = "${it.getCurrentFrequency()} FM"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) unbindService(serviceConnection)
    }
}