package com.android.basketballcounter

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException
import java.lang.Exception

private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5

class BeatBox(private val assets: AssetManager) {

    val sounds: List<Sound>

    private val soundPool = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        SoundPool.Builder().setMaxStreams(MAX_SOUNDS).build()
    } else {
        TODO("VERSION.SDK_INT < LOLLIPOP")
    }


    init {
        sounds = loadSounds()
    }

    fun release() {
        soundPool.release()
    }

    fun play(sound: Sound) {
        sound.soundId?.let { soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f) }

    }

    fun loadSounds(): List<Sound> {
        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            return emptyList()
        }

        val sounds = mutableListOf<Sound>()
        soundNames.forEach { fileName ->
            val assetPath = "$SOUNDS_FOLDER/$fileName"
            val sound = Sound(assetPath)
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.d(Debug, "Could not load sound $fileName", ioe)
            }
        }

        return sounds

    }

    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }
}