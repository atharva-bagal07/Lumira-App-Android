package com.example.lumira.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import kotlin.math.floor

object AstronomyEngine {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMoonPhase(date: LocalDate = LocalDate.now()): String {
        val year = date.year
        val month = date.monthValue
        val day = date.dayOfMonth

        var y = year.toDouble()
        var m = month.toDouble()
        val d = day.toDouble()

        if (m < 3) {
            y -= 1; m += 12
        }

        val a = floor(y / 100)
        val b = 2 - a + floor(a / 4)
        val jd = floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + d + b - 1524.5
        val daysSinceNew = (jd - 2451549.5) % 29.53058867
        val normalized = if (daysSinceNew < 0) daysSinceNew + 29.53058867 else daysSinceNew

        return when {
            normalized < 1.85 -> "New Moon"
            normalized < 7.38 -> "Waxing Crescent"
            normalized < 9.22 -> "First Quarter"
            normalized < 14.77 -> "Waxing Gibbous"
            normalized < 16.61 -> "Full Moon"
            normalized < 22.15 -> "Waning Gibbous"
            normalized < 23.99 -> "Last Quarter"
            normalized < 29.53 -> "Waning Crescent"
            else -> "New Moon"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMoonSign(date: LocalDate = LocalDate.now()): String {
        val dayOfYear = date.dayOfYear
        val index = ((dayOfYear + 5) / 2.46).toInt() % 12
        val signs = listOf(
            "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
            "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
        )
        return signs[index]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMercuryStatus(date: LocalDate = LocalDate.now()): String {
        val retrogradeRanges = listOf(
            LocalDate.of(2026, 1, 10) to LocalDate.of(2026, 1, 31),
            LocalDate.of(2026, 5, 10) to LocalDate.of(2026, 6, 3),
            LocalDate.of(2026, 9, 12) to LocalDate.of(2026, 10, 4)
        )
        return if (retrogradeRanges.any { date >= it.first && date <= it.second })
            "Mercury Retrograde" else "Mercury Direct"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getVenusStatus(date: LocalDate = LocalDate.now()): String {
        val retrogradeRanges = listOf(
            LocalDate.of(2025, 3, 1) to LocalDate.of(2025, 4, 12),
            LocalDate.of(2026, 10, 3) to LocalDate.of(2026, 11, 13)
        )
        return if (retrogradeRanges.any { date >= it.first && date <= it.second })
            "Venus Retrograde" else "Venus Direct"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMarsStatus(date: LocalDate = LocalDate.now()): String {
        val retrogradeRanges = listOf(
            LocalDate.of(2024, 12, 6) to LocalDate.of(2025, 2, 23),
            LocalDate.of(2026, 12, 9) to LocalDate.of(2027, 2, 19)
        )
        return if (retrogradeRanges.any { date >= it.first && date <= it.second })
            "Mars Retrograde" else "Mars Direct"
    }

    fun getZodiacFromDob(day: Int, month: Int): String {
        return when {
            (month == 3 && day >= 21) || (month == 4 && day <= 19) -> "Aries"
            (month == 4 && day >= 20) || (month == 5 && day <= 20) -> "Taurus"
            (month == 5 && day >= 21) || (month == 6 && day <= 20) -> "Gemini"
            (month == 6 && day >= 21) || (month == 7 && day <= 22) -> "Cancer"
            (month == 7 && day >= 23) || (month == 8 && day <= 22) -> "Leo"
            (month == 8 && day >= 23) || (month == 9 && day <= 22) -> "Virgo"
            (month == 9 && day >= 23) || (month == 10 && day <= 22) -> "Libra"
            (month == 10 && day >= 23) || (month == 11 && day <= 21) -> "Scorpio"
            (month == 11 && day >= 22) || (month == 12 && day <= 21) -> "Sagittarius"
            (month == 12 && day >= 22) || (month == 1 && day <= 19) -> "Capricorn"
            (month == 1 && day >= 20) || (month == 2 && day <= 18) -> "Aquarius"
            else -> "Pisces"
        }
    }

    fun getChineseZodiac(year: Int): String {
        val animals = listOf(
            "Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake",
            "Horse", "Goat", "Monkey", "Rooster", "Dog", "Pig"
        )
        return animals[(year - 4) % 12]
    }

    fun getLifePathNumber(day: Int, month: Int, year: Int): Int {
        val digits = "$day$month$year".map { it.digitToInt() }
        var sum = digits.sum()
        while (sum > 9 && sum != 11 && sum != 22 && sum != 33) {
            sum = sum.toString().map { it.digitToInt() }.sum()
        }
        return sum
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPersonalDayNumber(
        day: Int,
        month: Int,
        year: Int,
        today: LocalDate = LocalDate.now()
    ): Int {
        val personalYear = getLifePathNumber(today.dayOfMonth, today.monthValue, today.year)
        var sum = today.dayOfMonth + today.monthValue + personalYear
        while (sum > 9) {
            sum = sum.toString().map { it.digitToInt() }.sum()
        }
        return sum
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAstrologyContext(date: LocalDate = LocalDate.now()): String {
        val moonPhase = getMoonPhase(date)
        val moonSign = getMoonSign(date)
        val mercury = getMercuryStatus(date)
        val venus = getVenusStatus(date)
        val mars = getMarsStatus(date)
        return "Moon is in $moonPhase in $moonSign. $mercury. $venus. $mars."
    }
}