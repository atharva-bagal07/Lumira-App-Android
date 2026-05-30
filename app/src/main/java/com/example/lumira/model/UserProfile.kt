package com.example.lumira.model

data class UserProfile(
    val name: String = "",
    val dateOfBirth: String = "",
    val birthTime: String = "",
    val zodiacSign: String = "",
    val chineseZodiac: String = "",
    val lifePathNumber: Int = 0,
    val lifePhase: String = "",
    val notifHour: Int = 8,
    val subscriptionTier: String = "free"
)