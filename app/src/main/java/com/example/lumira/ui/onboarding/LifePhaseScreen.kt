package com.example.lumira.ui.onboarding

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
import com.example.lumira.ui.theme.*

data class LifePhase(
    val title: String,
    val subtitle: String,
    val symbol: String
)

val lifePhases = listOf(
    LifePhase("Finding my path", "Exploring who I am and what I want", "◎"),
    LifePhase("Building my career", "Focused on growth and ambition", "↑"),
    LifePhase("Navigating relationships", "Love, family and connection matter most", "☽"),
    LifePhase("Focusing on health", "My body and mind need attention", "✦"),
    LifePhase("Seeking purpose", "Looking for deeper meaning in life", "∞"),
)

@Composable
fun LifePhaseScreen(onContinue: (String) -> Unit) {
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
        Spacer(modifier = Modifier.height(48.dp))

        ProgressDots(total = 8, current = 4, activeColor = primary, inactiveColor = border)

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "Where are you in life?",
            style = MaterialTheme.typography.headlineMedium,
            color = textPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "This shapes the tone of your readings.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        lifePhases.forEach { phase ->
            val isSelected = selected == phase.title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isSelected) accentFill else surface)
                    .border(
                        width = if (isSelected) 1.5.dp else 0.5.dp,
                        color = if (isSelected) accentBorder else border,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { selected = phase.title }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = phase.symbol,
                    fontSize = 20.sp,
                    color = if (isSelected) primary else textSecondary,
                    modifier = Modifier.width(32.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = phase.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSelected) primary else textPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = phase.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSelected) primary else textSecondary,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        LumiraButton(
            text = "This is where I am",
            onClick = { if (selected.isNotEmpty()) onContinue(selected) },
            backgroundColor = if (selected.isEmpty()) border else primaryDark,
            textColor = if (selected.isEmpty()) textSecondary else if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}