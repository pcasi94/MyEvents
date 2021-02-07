package com.mimoupsa.myevents.data.preferences

import android.app.backup.BackupAgentHelper
import android.app.backup.SharedPreferencesBackupHelper
import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context): BackupAgentHelper() {

    override fun onCreate() {
        super.onCreate()
        val helper = SharedPreferencesBackupHelper(this, PREFS)
        addHelper(KEY,helper)
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS,0)

    var radius: Int
        get() = prefs.getInt(RADIUS, DEFAULT_VALUE)
        set(value) = prefs.edit().putInt(RADIUS, value).apply()

    companion object{
        private const val PREFS = "mimoupsa.myevents"
        private const val DEFAULT_VALUE = 0
        private const val RADIUS = "RADIUS"
        private const val KEY = "HELPER"
    }
}