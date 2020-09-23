package com.android.basketballcounter

import android.app.Application

class TeamIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TeamRepository.initialize(this)
    }
}