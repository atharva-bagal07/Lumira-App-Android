package com.example.lumira.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumira.data.UserPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LumiraViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPreferences(application)

    // ─── State ───────────────────────────────────────────
    val userName: StateFlow<String> = prefs.userName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val userZodiac: StateFlow<String> = prefs.userZodiac
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val streak: StateFlow<Int> = prefs.streak
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val onboardingDone: StateFlow<Boolean> = prefs.onboardingDone
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val notifHour: StateFlow<Int> = prefs.notifHour
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 7)

    // ─── Onboarding Saves ────────────────────────────────
    fun saveName(name: String) = viewModelScope.launch {
        prefs.saveName(name)
    }

    fun saveZodiac(zodiac: String) = viewModelScope.launch {
        prefs.saveZodiac(zodiac)
    }

    fun saveNotifHour(hour: Int) = viewModelScope.launch {
        prefs.saveNotifHour(hour)
    }

    fun completeOnboarding() = viewModelScope.launch {
        prefs.setOnboardingDone()
    }

    // ─── Streak Logic ────────────────────────────────────
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAndUpdateStreak() = viewModelScope.launch {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        prefs.lastOpened.first().let { lastOpened ->
            when {
                lastOpened.isEmpty() -> {
                    // First time ever
                    prefs.saveStreak(1)
                    prefs.saveLastOpened(today)
                }

                lastOpened == today -> {
                    // Already opened today, do nothing
                }

                lastOpened == LocalDate.now().minusDays(1)
                    .format(DateTimeFormatter.ISO_DATE) -> {
                    // Opened yesterday — increment streak
                    prefs.streak.first().let { current ->
                        prefs.saveStreak(current + 1)
                    }
                    prefs.saveLastOpened(today)
                }

                else -> {
                    // Missed a day — reset streak
                    prefs.saveStreak(1)
                    prefs.saveLastOpened(today)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun markReflected() = viewModelScope.launch {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        prefs.lastOpened.first().let { lastOpened ->
            if (lastOpened != today) {
                prefs.streak.first().let { current ->
                    prefs.saveStreak(current + 1)
                }
                prefs.saveLastOpened(today)
            }
        }
    }
}