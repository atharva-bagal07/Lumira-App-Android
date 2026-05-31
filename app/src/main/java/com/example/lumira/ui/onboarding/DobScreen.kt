package com.example.lumira.ui.onboarding

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumira.data.AstronomyEngine
import com.example.lumira.ui.theme.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DobScreen(onContinue: (String, String, String, Int) -> Unit) {
    val isDark = isNightTime()
    val bg = if (isDark) DarkBackground else LightBackground
    val textPrimary = if (isDark) DarkTextPrimary else LightTextPrimary
    val textSecondary = if (isDark) DarkTextSecondary else LightTextSecondary
    val primary = if (isDark) DarkPrimary else LightPrimary
    val primaryDark = if (isDark) DarkPrimaryDark else LightPrimaryDark
    val accentFill = if (isDark) DarkAccentFill else LightAccentFill
    val accentBorder = if (isDark) DarkAccentBorder else LightAccentBorder
    val border = if (isDark) DarkBorder else LightBorder
    val surface = if (isDark) DarkSurface else LightSurface

    val days = (1..31).toList()
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    val currentYear = java.time.LocalDate.now().year
    val years = (currentYear - 80..currentYear - 5).toList().reversed()

    var selectedDay by remember { mutableStateOf(1) }
    var selectedMonth by remember { mutableStateOf(1) }
    var selectedYear by remember { mutableStateOf(2000) }

    var dayExpanded by remember { mutableStateOf(false) }
    var monthExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        ProgressDots(total = 8, current = 2, activeColor = primary, inactiveColor = border)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "When were you born?",
            style = MaterialTheme.typography.headlineMedium,
            color = textPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We use this to calculate your zodiac and personal numbers.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(surface)
                        .border(
                            0.5.dp,
                            if (dayExpanded) primary else border,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { dayExpanded = true }
                        .padding(14.dp)
                ) {
                    Text(
                        text = selectedDay.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = textPrimary
                    )
                }
                DropdownMenu(
                    expanded = dayExpanded,
                    onDismissRequest = { dayExpanded = false },
                    modifier = Modifier.background(surface)
                ) {
                    days.forEach { day ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    day.toString(),
                                    color = if (day == selectedDay) primary else textPrimary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = { selectedDay = day; dayExpanded = false }
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(2f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(surface)
                        .border(
                            0.5.dp,
                            if (monthExpanded) primary else border,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { monthExpanded = true }
                        .padding(14.dp)
                ) {
                    Text(
                        text = months[selectedMonth - 1],
                        style = MaterialTheme.typography.bodyMedium,
                        color = textPrimary
                    )
                }
                DropdownMenu(
                    expanded = monthExpanded,
                    onDismissRequest = { monthExpanded = false },
                    modifier = Modifier.background(surface)
                ) {
                    months.forEachIndexed { index, month ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    month,
                                    color = if (index + 1 == selectedMonth) primary else textPrimary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = { selectedMonth = index + 1; monthExpanded = false }
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1.5f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(surface)
                        .border(
                            0.5.dp,
                            if (yearExpanded) primary else border,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { yearExpanded = true }
                        .padding(14.dp)
                ) {
                    Text(
                        text = selectedYear.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = textPrimary
                    )
                }
                DropdownMenu(
                    expanded = yearExpanded,
                    onDismissRequest = { yearExpanded = false },
                    modifier = Modifier.background(surface)
                ) {
                    years.forEach { year ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    year.toString(),
                                    color = if (year == selectedYear) primary else textPrimary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = { selectedYear = year; yearExpanded = false }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val zodiac = AstronomyEngine.getZodiacFromDob(selectedDay, selectedMonth)
        val chineseZodiac = AstronomyEngine.getChineseZodiac(selectedYear)
        val lifePath = AstronomyEngine.getLifePathNumber(selectedDay, selectedMonth, selectedYear)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(accentFill)
                .border(0.5.dp, accentBorder, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = zodiac,
                    style = MaterialTheme.typography.titleMedium,
                    color = primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Year of the $chineseZodiac · Life Path $lifePath",
                    style = MaterialTheme.typography.bodyMedium,
                    color = primary,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        LumiraButton(
            text = "This is my birthday",
            onClick = {
                val dob = "$selectedYear-${
                    selectedMonth.toString().padStart(2, '0')
                }-${selectedDay.toString().padStart(2, '0')}"
                onContinue(dob, zodiac, chineseZodiac, lifePath)
            },
            backgroundColor = primaryDark,
            textColor = if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}