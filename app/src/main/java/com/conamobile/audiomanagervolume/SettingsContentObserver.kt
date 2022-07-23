package com.conamobile.audiomanagervolume

import android.database.ContentObserver
import android.os.Handler
import androidx.lifecycle.MutableLiveData

class SettingsContentObserver(handler: Handler?) : ContentObserver(handler) {
    val systemVolume = MutableLiveData<Boolean>()
    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        systemVolume.value = selfChange
    }
}