package com.turk.musicsystem.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class RadioPlayerService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val binder = RadioBinder()
    private var currentStationIndex = 0

    data class RadioStation(val name: String, val frequency: String, val streamUrl: String)

    private val radioStations = listOf(
        RadioStation("TRT 1", "87.6", "https://stream.trtvod.com/trt1"),
        RadioStation("TRT FM", "95.1", "https://stream.trtvod.com/trtfm"),
        RadioStation("Power FM", "93.2", "https://powerfm.com/stream"),
        RadioStation("Metro FM", "97.2", "https://metrofm.com/stream"),
        RadioStation("Joy FM", "94.9", "https://joyfm.com/stream")
    )

    inner class RadioBinder : Binder() {
        fun getService(): RadioPlayerService = this@RadioPlayerService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
    }

    fun play() {
        if (mediaPlayer?.isPlaying == false) {
            val station = radioStations[currentStationIndex]
            try {
                mediaPlayer?.apply {
                    reset()
                    setDataSource(station.streamUrl)
                    prepareAsync()
                    setOnPreparedListener { it.start() }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun nextStation() {
        currentStationIndex = (currentStationIndex + 1) % radioStations.size
        if (mediaPlayer?.isPlaying == true) {
            play()
        }
    }

    fun previousStation() {
        currentStationIndex = if (currentStationIndex > 0) currentStationIndex - 1 else radioStations.size - 1
        if (mediaPlayer?.isPlaying == true) {
            play()
        }
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false

    fun setVolume(volume: Int) {
        val vol = volume.toFloat() / 100
        mediaPlayer?.setVolume(vol, vol)
    }

    fun getCurrentStationName(): String = radioStations[currentStationIndex].name

    fun getCurrentFrequency(): String = radioStations[currentStationIndex].frequency

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}