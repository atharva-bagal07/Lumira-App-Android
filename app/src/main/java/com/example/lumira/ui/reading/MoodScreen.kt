package com.example.lumira.ui.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumira.ui.onboarding.LumiraButton
import com.example.lumira.ui.theme.*
import com.example.lumira.viewmodel.ReadingViewModel

data class Mood(
    val label: String,
    val emoji: String
)

val moods = listOf(
    Mood("Hopeful", "🌅"),
    Mood("Anxious", "🌀"),
    Mood("Grateful", "✨"),
    Mood("Confused", "🌫"),
    Mood("Energised", "⚡"),
    Mood("Tired", "🌙"),
    Mood("Calm", "🍃"),
    Mood("Sad", "🌧"),
    Mood("Excited", "🔥"),
    Mood("Frustrated", "🌊"),
)

@Composable
fun MoodScreen(
    readingViewModel: ReadingViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit
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

    var selected by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                fontSize = 20.sp,
                color = textSecondary,
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(end = 16.dp)
            )
            Text(
                text = "How are you feeling?",
                style = MaterialTheme.typography.titleMedium,
                color = textPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your mood shapes your reading.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        moods.chunked(2).forEach { rowMoods ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowMoods.forEach { mood ->
                    val isSelected = selected == mood.label
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) accentFill else surface)
                            .border(
                                width = if (isSelected) 1.5.dp else 0.5.dp,
                                color = if (isSelected) accentBorder else border,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { selected = mood.label }
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = mood.emoji,
                            fontSize = 18.sp
                        )
                        Text(
                            text = mood.label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isSelected) primary else textPrimary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
                if (rowMoods.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        LumiraButton(
            text = "Continue",
            onClick = {
                if (selected.isNotEmpty()) {
                    readingViewModel.setMood(selected)
                    onContinue()
                }
            },
            backgroundColor = if (selected.isEmpty()) border else primaryDark,
            textColor = if (selected.isEmpty()) textSecondary else if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}