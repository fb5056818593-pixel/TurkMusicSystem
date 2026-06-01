package com.turk.musicsystem.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicPlayerService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val binder = MusicBinder()
    private var currentTrackIndex = 0
    private var playlistPaths = mutableListOf<String>()
    private var audioSessionId = 0

    inner class MusicBinder : Binder() {
        fun getService(): MusicPlayerService = this@MusicPlayerService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        audioSessionId = mediaPlayer!!.audioSessionId
    }

    fun play() {
        if (mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
        }
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun loadTrack(path: String) {
        try {
            mediaPlayer?.apply {
                reset()
                setDataSource(path)
                prepare()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun nextTrack() {
        if (playlistPaths.isNotEmpty()) {
            currentTrackIndex = (currentTrackIndex + 1) % playlistPaths.size
            loadTrack(playlistPaths[currentTrackIndex])
            play()
        }
    }

    fun previousTrack() {
        if (playlistPaths.isNotEmpty()) {
            currentTrackIndex = if (currentTrackIndex > 0) currentTrackIndex - 1 else playlistPaths.size - 1
            loadTrack(playlistPaths[currentTrackIndex])
            play()
        }
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false

    fun getDuration(): Long = mediaPlayer?.duration?.toLong() ?: 0L

    fun getCurrentPosition(): Long = mediaPlayer?.currentPosition?.toLong() ?: 0L

    fun setVolume(volume: Int) {
        val vol = volume.toFloat() / 100
        mediaPlayer?.setVolume(vol, vol)
    }

    fun getAudioSessionId(): Int = audioSessionId

    fun getCurrentTrackName(): String = "Müzik Parçası ${currentTrackIndex + 1}"

    fun getCurrentArtist(): String = "Sanatçı"

    fun setPlaylist(paths: List<String>) {
        playlistPaths = paths.toMutableList()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}