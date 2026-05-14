package com.example.lumira.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "lumira_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        val KEY_NAME = stringPreferencesKey("user_name")
        val KEY_ZODIAC = stringPreferencesKey("user_zodiac")
        val KEY_NOTIF_HOUR = intPreferencesKey("notif_hour")
        val KEY_STREAK = intPreferencesKey("streak")
        val KEY_LAST_OPENED = stringPreferencesKey("last_opened")
        val KEY_ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
    }

    // Read
    val userName: Flow<String> = context.dataStore.data.map { it[KEY_NAME] ?: "" }
    val userZodiac: Flow<String> = context.dataStore.data.map { it[KEY_ZODIAC] ?: "" }
    val notifHour: Flow<Int> = context.dataStore.data.map { it[KEY_NOTIF_HOUR] ?: 7 }
    val streak: Flow<Int> = context.dataStore.data.map { it[KEY_STREAK] ?: 0 }
    val lastOpened: Flow<String> = context.dataStore.data.map { it[KEY_LAST_OPENED] ?: "" }
    val onboardingDone: Flow<Boolean> =
        context.dataStore.data.map { it[KEY_ONBOARDING_DONE] ?: false }

    // Write
    suspend fun saveName(name: String) {
        context.dataStore.edit { it[KEY_NAME] = name }
    }

    suspend fun saveZodiac(zodiac: String) {
        context.dataStore.edit { it[KEY_ZODIAC] = zodiac }
    }

    suspend fun saveNotifHour(hour: Int) {
        context.dataStore.edit { it[KEY_NOTIF_HOUR] = hour }
    }

    suspend fun saveStreak(streak: Int) {
        context.dataStore.edit { it[KEY_STREAK] = streak }
    }

    suspend fun saveLastOpened(date: String) {
        context.dataStore.edit { it[KEY_LAST_OPENED] = date }
    }

    suspend fun setOnboardingDone() {
        context.dataStore.edit { it[KEY_ONBOARDING_DONE] = true }
    }
}