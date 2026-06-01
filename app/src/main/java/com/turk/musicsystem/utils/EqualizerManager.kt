package com.turk.musicsystem.utils

import android.content.Context
import android.media.audiofx.Equalizer

class EqualizerManager(private val context: Context) {
    private var equalizer: Equalizer? = null

    fun setEqualizer(eq: Equalizer) {
        this.equalizer = eq
    }

    fun setNormalPreset() {
        equalizer?.let {
            for (i in 0 until it.numberOfBands) {
                it.setBandLevel(i.toShort(), 0)
            }
        }
    }

    fun setBassBoost() {
        equalizer?.let {
            val bandCount = it.numberOfBands
            for (i in 0 until bandCount / 2) {
                it.setBandLevel(i.toShort(), 1500)
            }
        }
    }

    fun setTrebleBoost() {
        equalizer?.let {
            val bandCount = it.numberOfBands
            for (i in (bandCount / 2) until bandCount) {
                it.setBandLevel(i.toShort(), 1500)
            }
        }
    }

    fun setLivePreset() {
        equalizer?.let {
            val bandCount = it.numberOfBands
            for (i in 0 until bandCount) {
                val level = if (i % 2 == 0) 1000.toShort() else (-500).toShort()
                it.setBandLevel(i.toShort(), level)
            }
        }
    }

    fun release() {
        equalizer?.release()
    }
}