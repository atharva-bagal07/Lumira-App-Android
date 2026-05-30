package com.example.lumira.ui.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumira.ui.onboarding.LumiraButton
import com.example.lumira.ui.onboarding.LumiraGhostButton
import com.example.lumira.ui.theme.*
import com.example.lumira.viewmodel.LumiraViewModel
import com.example.lumira.viewmodel.ReadingViewModel

@Composable
fun ReadingResultScreen(
    lumiraViewModel: LumiraViewModel,
    readingViewModel: ReadingViewModel,
    onDone: () -> Unit,
    onAnotherReading: () -> Unit
) {
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

    val readingResult by readingViewModel.readingResult.collectAsState()
    val selectedStyle by readingViewModel.selectedStyle.collectAsState()
    val selectedArea by readingViewModel.selectedArea.collectAsState()
    val selectedMood by readingViewModel.selectedMood.collectAsState()
    val question by readingViewModel.question.collectAsState()
    val readingsToday by lumiraViewModel.readingsToday.collectAsState()
    val subscriptionTier by lumiraViewModel.subscriptionTier.collectAsState()

    val maxReadings = if (subscriptionTier == "premium") 3 else 1
    val canReadAgain = readingsToday < maxReadings

    val parts = readingResult.split("Affirmation:")
    val mainReading = parts.firstOrNull()?.trim() ?: readingResult
    val affirmation = if (parts.size > 1) parts[1].trim() else ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "$selectedStyle Reading",
                    style = MaterialTheme.typography.titleMedium,
                    color = textPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$selectedArea · $selectedMood",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary,
                    fontSize = 13.sp
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(99.dp))
                    .background(accentFill)
                    .border(0.5.dp, accentBorder, RoundedCornerShape(99.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "$readingsToday/$maxReadings today",
                    style = MaterialTheme.typography.labelSmall,
                    color = primary,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(surface)
                .border(0.5.dp, border, RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "YOUR QUESTION",
                style = MaterialTheme.typography.labelSmall,
                color = textSecondary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = question,
                style = MaterialTheme.typography.bodyMedium,
                color = textPrimary,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(surface)
                .border(0.5.dp, border, RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Text(
                text = "YOUR READING",
                style = MaterialTheme.typography.labelSmall,
                color = textSecondary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = mainReading,
                style = MaterialTheme.typography.bodyLarge,
                color = textPrimary,
                lineHeight = 30.sp
            )
        }

        if (affirmation.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(accentFill)
                    .border(0.5.dp, accentBorder, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Text(
                    text = "AFFIRMATION",
                    style = MaterialTheme.typography.labelSmall,
                    color = primary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "\"$affirmation\"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = primary,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        LumiraButton(
            text = "Done",
            onClick = {
                readingViewModel.reset()
                onDone()
            },
            backgroundColor = primaryDark,
            textColor = if (isDark) DarkTextPrimary else LightBackground
        )

        if (canReadAgain) {
            Spacer(modifier = Modifier.height(12.dp))
            LumiraGhostButton(
                text = "Get another reading",
                onClick = {
                    readingViewModel.reset()
                    onAnotherReading()
                },
                textColor = primary,
                borderColor = accentBorder
            )
        } else {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(surface)
                    .border(0.5.dp, border, RoundedCornerShape(12.dp))
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (subscriptionTier == "premium")
                        "You've used all $maxReadings readings for today."
                    else
                        "Upgrade to Oracle for up to 3 readings per day.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}