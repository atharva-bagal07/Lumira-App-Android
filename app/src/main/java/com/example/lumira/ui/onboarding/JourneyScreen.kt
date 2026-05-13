package com.example.lumira.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

data class JourneyStage(
    val label: String,
    val symbol: String,
    val isActive: Boolean
)

val journeyStages = listOf(
    JourneyStage("Seed", "◉", true),
    JourneyStage("Moon", "☽", false),
    JourneyStage("Star", "✦", false),
    JourneyStage("Orbit", "◎", false),
    JourneyStage("Cosmic", "∞", false),
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
    val border = if (isDark) DarkBorder else LightBorder

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        ProgressDots(total = 5, current = 4, activeColor = primary, inactiveColor = border)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Your journey begins.",
            style = MaterialTheme.typography.headlineMedium,
            color = textPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Each day you reflect, you grow.\nWatch your path unfold.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Journey stages row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            journeyStages.forEachIndexed { index, stage ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Circle
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(if (stage.isActive) primary else accentFill),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stage.symbol,
                            fontSize = 18.sp,
                            color = if (stage.isActive) bg else textSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stage.label,
                        fontSize = 11.sp,
                        color = if (stage.isActive) primary else textSecondary,
                        fontWeight = if (stage.isActive) FontWeight.Medium else FontWeight.Normal
                    )
                }

                // Connector line between stages
                if (index < journeyStages.size - 1) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .width(12.dp)
                            .height(1.dp)
                            .background(border)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Info card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(accentFill)
                .padding(16.dp)
        ) {
            Text(
                text = "Reflect daily to unlock each stage.\nYour streak is your light.",
                style = MaterialTheme.typography.bodyMedium,
                color = primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        LumiraButton(
            text = "See today's guidance",
            onClick = onContinue,
            backgroundColor = primaryDark,
            textColor = if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}