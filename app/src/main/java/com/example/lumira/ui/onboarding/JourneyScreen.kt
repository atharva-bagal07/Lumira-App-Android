package com.example.lumira.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumira.ui.theme.*

data class Stage(
    val symbol: String,
    val name: String,
    val days: String,
    val description: String,
    val isActive: Boolean
)

val stages = listOf(
    Stage("◉", "Seed", "Day 1–7", "Your journey starts here.", true),
    Stage("☽", "Moon", "Day 8–21", "A habit begins to form.", false),
    Stage("☆", "Star", "Day 22–50", "Reflection becomes natural.", false),
    Stage("◎", "Orbit", "Day 51–100", "Your energy is aligned.", false),
    Stage("∞", "Cosmic", "Day 101+", "You've found your rhythm.", false),
)

@Composable
fun JourneyScreen(onContinue: () -> Unit) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        ProgressDots(total = 8, current = 6, activeColor = primary, inactiveColor = border)

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "Your journey begins.",
            style = MaterialTheme.typography.headlineMedium,
            color = textPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Reflect daily to grow through each stage.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Stage list
        stages.forEach { stage ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(surface)
                    .border(0.5.dp, border, RoundedCornerShape(14.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Symbol circle
                Text(
                    text = stage.symbol,
                    fontSize = 28.sp,
                    color = primary,
                    modifier = Modifier.width(44.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stage.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = textPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stage.days,
                            style = MaterialTheme.typography.labelSmall,
                            color = textSecondary,
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stage.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textSecondary,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        LumiraButton(
            text = "See today's guidance",
            onClick = onContinue,
            backgroundColor = primaryDark,
            textColor = if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}