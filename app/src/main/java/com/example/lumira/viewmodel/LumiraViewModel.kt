package com.example.lumira.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumira.data.NotificationScheduler
import com.example.lumira.data.UserPreferences
import com.example.lumira.data.FirestoreRepository
import com.example.lumira.data.AstronomyEngine
import com.example.lumira.model.ReadingSession
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

class LumiraViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPreferences(application)
    private val repository = FirestoreRepository()

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    val userName: StateFlow<String> = prefs.userName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val userDob: StateFlow<String> = prefs.userDob
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val userBirthTime: StateFlow<String> = prefs.userBirthTime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val userZodiac: StateFlow<String> = prefs.userZodiac
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val userChineseZodiac: StateFlow<String> = prefs.userChineseZodiac
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val lifePathNumber: StateFlow<Int> = prefs.lifePathNumber
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val lifePhase: StateFlow<String> = prefs.lifePhase
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val streak: StateFlow<Int> = prefs.streak
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val onboardingDone: StateFlow<Boolean> = prefs.onboardingDone
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val notifHour: StateFlow<Int> = prefs.notifHour
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 8)

    val subscriptionTier: StateFlow<String> = prefs.subscriptionTier
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "free")

    val readingsToday: StateFlow<Int> = prefs.readingsToday
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val usedAreasToday: StateFlow<String> = prefs.usedAreasToday
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    private val _dailyGuidance = MutableStateFlow("")
    val dailyGuidance: StateFlow<String> = _dailyGuidance

    private val _readingHistory = MutableStateFlow<List<ReadingSession>>(emptyList())
    val readingHistory: StateFlow<List<ReadingSession>> = _readingHistory

    init {
        viewModelScope.launch {
            try {
                prefs.onboardingDone.first()
                _isReady.value = true
            } catch (e: Exception) {
                _isReady.value = true
            }
        }
    }

    fun saveName(name: String) = viewModelScope.launch { prefs.saveName(name) }

    fun saveDob(dob: String, zodiac: String, chineseZodiac: String, lifePath: Int) =
        viewModelScope.launch {
            prefs.saveDob(dob)
            prefs.saveZodiac(zodiac)
            prefs.saveChineseZodiac(chineseZodiac)
            prefs.saveLifePath(lifePath)
        }

    fun saveBirthTime(time: String) = viewModelScope.launch { prefs.saveBirthTime(time) }

    fun saveLifePhase(phase: String) = viewModelScope.launch { prefs.saveLifePhase(phase) }

    fun saveNotifHour(hour: Int) = viewModelScope.launch { prefs.saveNotifHour(hour) }

    fun saveSubscription(tier: String) = viewModelScope.launch { prefs.saveSubscription(tier) }

    fun completeOnboarding() = viewModelScope.launch { prefs.setOnboardingDone() }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleNotifications(context: Context, teaser: String) {
        NotificationScheduler.scheduleDailyNotifications(context, teaser)
    }

    fun cancelNotifications(context: Context) {
        NotificationScheduler.cancelAll(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAndUpdateStreak() = viewModelScope.launch {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE)
        val lastOpened = prefs.lastOpened.first()
        when {
            lastOpened.isEmpty() -> {}
            lastOpened == today -> {}
            lastOpened != yesterday -> prefs.saveStreak(0)
        }
        resetReadingsIfNewDay(today)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun markReflected() = viewModelScope.launch {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        val lastOpened = prefs.lastOpened.first()
        if (lastOpened != today) {
            val current = prefs.streak.first()
            prefs.saveStreak(current + 1)
            prefs.saveLastOpened(today)
        }
    }

    private suspend fun resetReadingsIfNewDay(today: String) {
        val readingsDate = prefs.readingsDate.first()
        if (readingsDate != today) {
            prefs.saveReadingsToday(0)
            prefs.saveReadingsDate(today)
            prefs.saveUsedAreasToday("")
        }
    }

    fun incrementReadingsToday() = viewModelScope.launch {
        val current = prefs.readingsToday.first()
        prefs.saveReadingsToday(current + 1)
    }

    fun addUsedArea(area: String) = viewModelScope.launch {
        val current = prefs.usedAreasToday.first()
        val updated = if (current.isEmpty()) area else "$current,$area"
        prefs.saveUsedAreasToday(updated)
    }

    fun getMaxReadings(): Int {
        return if (subscriptionTier.value == "premium") 3 else 1
    }

    fun canStartReading(): Boolean {
        return readingsToday.value < getMaxReadings()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchDailyGuidance(zodiac: String) = viewModelScope.launch {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        val result = repository.getDailyGuidance(zodiac, today)
        if (result != null) {
            _dailyGuidance.value = result.guidance
        }
    }

    fun saveReadingSession(session: ReadingSession) = viewModelScope.launch {
        repository.saveReadingSession(session)
        incrementReadingsToday()
        addUsedArea(session.areaOfLife)
    }

    fun fetchReadingHistory(zodiac: String) = viewModelScope.launch {
        val isPremium = prefs.subscriptionTier.first() == "premium"
        val history = repository.getReadingHistory(zodiac, if (isPremium) 20 else 1)
        _readingHistory.value = history
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAstrologyContext(): String = AstronomyEngine.getAstrologyContext()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPersonalDayNumber(): Int {
        val dob = userDob.value
        if (dob.isEmpty()) return 1
        return try {
            val parts = dob.split("-")
            AstronomyEngine.getPersonalDayNumber(
                parts[2].toInt(), parts[1].toInt(), parts[0].toInt()
            )
        } catch (e: Exception) {
            1
        }
    }
}