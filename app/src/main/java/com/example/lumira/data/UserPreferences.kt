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
        val KEY_DOB = stringPreferencesKey("user_dob")
        val KEY_BIRTH_TIME = stringPreferencesKey("user_birth_time")
        val KEY_ZODIAC = stringPreferencesKey("user_zodiac")
        val KEY_CHINESE_ZODIAC = stringPreferencesKey("user_chinese_zodiac")
        val KEY_LIFE_PATH = intPreferencesKey("life_path_number")
        val KEY_LIFE_PHASE = stringPreferencesKey("life_phase")
        val KEY_NOTIF_HOUR = intPreferencesKey("notif_hour")
        val KEY_STREAK = intPreferencesKey("streak")
        val KEY_LAST_OPENED = stringPreferencesKey("last_opened")
        val KEY_ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        val KEY_SUBSCRIPTION = stringPreferencesKey("subscription_tier")
        val KEY_READINGS_TODAY = intPreferencesKey("readings_today")
        val KEY_READINGS_DATE = stringPreferencesKey("readings_date")
        val KEY_USED_AREAS_TODAY = stringPreferencesKey("used_areas_today")
    }

    val userName: Flow<String> = context.dataStore.data.map { it[KEY_NAME] ?: "" }
    val userDob: Flow<String> = context.dataStore.data.map { it[KEY_DOB] ?: "" }
    val userBirthTime: Flow<String> = context.dataStore.data.map { it[KEY_BIRTH_TIME] ?: "" }
    val userZodiac: Flow<String> = context.dataStore.data.map { it[KEY_ZODIAC] ?: "" }
    val userChineseZodiac: Flow<String> =
        context.dataStore.data.map { it[KEY_CHINESE_ZODIAC] ?: "" }
    val lifePathNumber: Flow<Int> = context.dataStore.data.map { it[KEY_LIFE_PATH] ?: 0 }
    val lifePhase: Flow<String> = context.dataStore.data.map { it[KEY_LIFE_PHASE] ?: "" }
    val notifHour: Flow<Int> = context.dataStore.data.map { it[KEY_NOTIF_HOUR] ?: 8 }
    val streak: Flow<Int> = context.dataStore.data.map { it[KEY_STREAK] ?: 0 }
    val lastOpened: Flow<String> = context.dataStore.data.map { it[KEY_LAST_OPENED] ?: "" }
    val onboardingDone: Flow<Boolean> =
        context.dataStore.data.map { it[KEY_ONBOARDING_DONE] ?: false }
    val subscriptionTier: Flow<String> =
        context.dataStore.data.map { it[KEY_SUBSCRIPTION] ?: "free" }
    val readingsToday: Flow<Int> = context.dataStore.data.map { it[KEY_READINGS_TODAY] ?: 0 }
    val readingsDate: Flow<String> = context.dataStore.data.map { it[KEY_READINGS_DATE] ?: "" }
    val usedAreasToday: Flow<String> = context.dataStore.data.map { it[KEY_USED_AREAS_TODAY] ?: "" }

    suspend fun saveName(name: String) = context.dataStore.edit { it[KEY_NAME] = name }
    suspend fun saveDob(dob: String) = context.dataStore.edit { it[KEY_DOB] = dob }
    suspend fun saveBirthTime(time: String) = context.dataStore.edit { it[KEY_BIRTH_TIME] = time }
    suspend fun saveZodiac(zodiac: String) = context.dataStore.edit { it[KEY_ZODIAC] = zodiac }
    suspend fun saveChineseZodiac(cz: String) =
        context.dataStore.edit { it[KEY_CHINESE_ZODIAC] = cz }

    suspend fun saveLifePath(num: Int) = context.dataStore.edit { it[KEY_LIFE_PATH] = num }
    suspend fun saveLifePhase(phase: String) = context.dataStore.edit { it[KEY_LIFE_PHASE] = phase }
    suspend fun saveNotifHour(hour: Int) = context.dataStore.edit { it[KEY_NOTIF_HOUR] = hour }
    suspend fun saveStreak(streak: Int) = context.dataStore.edit { it[KEY_STREAK] = streak }
    suspend fun saveLastOpened(date: String) = context.dataStore.edit { it[KEY_LAST_OPENED] = date }
    suspend fun setOnboardingDone() = context.dataStore.edit { it[KEY_ONBOARDING_DONE] = true }
    suspend fun saveSubscription(tier: String) =
        context.dataStore.edit { it[KEY_SUBSCRIPTION] = tier }

    suspend fun saveReadingsToday(count: Int) =
        context.dataStore.edit { it[KEY_READINGS_TODAY] = count }

    suspend fun saveReadingsDate(date: String) =
        context.dataStore.edit { it[KEY_READINGS_DATE] = date }

    suspend fun saveUsedAreasToday(areas: String) =
        context.dataStore.edit { it[KEY_USED_AREAS_TODAY] = areas }
}