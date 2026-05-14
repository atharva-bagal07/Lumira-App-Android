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

data class NotificationTime(
    val label: String,
    val subtitle: String,
    val emoji: String,
    val hour: Int
)

val notificationTimes = listOf(
    NotificationTime("Morning", "First reminder when your day begins", "🌅", 7),
    NotificationTime("Afternoon", "First reminder at midday", "☀️", 12),
    NotificationTime("Evening", "First reminder in the evening", "🌙", 20),
)
@Composable
fun NotificationScreen(onContinue: (Int) -> Unit) {
    val isDark = isNightTime()

    val bg = if (isDark) DarkBackground else LightBackground
    val textPrimary = if (isDark) DarkTextPrimary else LightTextPrimary
    val textSecondary = if (isDark) DarkTextSecondary else LightTextSecondary
    val textHint = if (isDark) DarkTextHint else LightTextHint
    val primary = if (isDark) DarkPrimary else LightPrimary
    val primaryDark = if (isDark) DarkPrimaryDark else LightPrimaryDark
    val accentFill = if (isDark) DarkAccentFill else LightAccentFill
    val accentBorder = if (isDark) DarkAccentBorder else LightAccentBorder
    val border = if (isDark) DarkBorder else LightBorder
    val surface = if (isDark) DarkSurface else LightSurface

    var selected by remember { mutableStateOf(notificationTimes[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        ProgressDots(total = 5, current = 3, activeColor = primary, inactiveColor = border)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "When would you like your guidance?",
            style = MaterialTheme.typography.headlineMedium,
            color = textPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "We'll nudge you until you've reflected.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Time options
        notificationTimes.forEach { time ->
            val isSelected = selected == time
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
                    .clickable { selected = time }
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = time.emoji,
                    fontSize = 20.sp,
                    fontFamily = Almendra,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) primary else textSecondary
                )
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = time.label,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isSelected) textPrimary else textPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = time.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textSecondary,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        LumiraButton(
            text = "Set my reminder",
            onClick = { onContinue(selected.hour) },
            backgroundColor = primaryDark,
            textColor = if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}