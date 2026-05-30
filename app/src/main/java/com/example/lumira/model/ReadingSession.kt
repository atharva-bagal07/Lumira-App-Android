package com.example.lumira.model

data class ReadingSession(
    val id: String = "",
    val date: String = "",
    val readingStyle: String = "",
    val areaOfLife: String = "",
    val mood: String = "",
    val question: String = "",
    val response: String = "",
    val zodiacSign: String = "",
    val timestamp: Long = 0L
)