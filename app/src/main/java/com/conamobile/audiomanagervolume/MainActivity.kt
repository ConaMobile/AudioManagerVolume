package com.conamobile.audiomanagervolume

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.conamobile.audiomanagervolume.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mSettingsContentObserver: SettingsContentObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings()
        audio()
    }

    private fun settings() {
        mSettingsContentObserver = SettingsContentObserver(Handler(Looper.getMainLooper()))
        this.applicationContext.contentResolver.registerContentObserver(
            Settings.System.CONTENT_URI, true,
            mSettingsContentObserver)
    }

    private fun audio() {

        binding.apply {
            button1.setOnClickListener {
                audioManager().adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
            }
            button2.setOnClickListener {
                audioManager().adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
            }

            seekBar.maxValue = audioManager().getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            seekBar.progress = audioManager().getStreamVolume(AudioManager.STREAM_MUSIC)

            seekBar.setOnProgressChangeListener {
                audioManager().setStreamVolume(AudioManager.STREAM_MUSIC, it, 0)
            }
            mSettingsContentObserver.systemVolume.observe(this@MainActivity) {
                seekBar.progress = audioManager().getStreamVolume(AudioManager.STREAM_MUSIC)
            }
        }
    }

    private fun audioManager(): AudioManager {
        return applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }
}