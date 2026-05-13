package com.example.lumira.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import com.example.lumira.ui.theme.*

@Composable
fun HomeScreen() {
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

    var reflected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Lumira",
                    style = MaterialTheme.typography.displayLarge,
                    color = textPrimary,
                    fontSize = 32.sp
                )
                Text(
                    text = "Wednesday, May 13",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary,
                    fontSize = 14.sp
                )
            }
            // Streak pill
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(99.dp))
                    .background(accentFill)
                    .border(0.5.dp, accentBorder, RoundedCornerShape(99.dp))
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "◉", fontSize = 12.sp, color = primary)
                Text(
                    text = "7 days aligned",
                    style = MaterialTheme.typography.labelMedium,
                    color = primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Sign + reading label
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Aries · Mar 24",
                style = MaterialTheme.typography.bodyMedium,
                color = textSecondary,
                fontSize = 13.sp
            )
            Text(
                text = "Today's reading",
                style = MaterialTheme.typography.bodyMedium,
                color = primary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Guidance card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(surface)
                .border(0.5.dp, border, RoundedCornerShape(16.dp))
                .padding(18.dp)
        ) {
            Text(
                text = "TODAY'S GUIDANCE",
                style = MaterialTheme.typography.labelSmall,
                color = textSecondary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Today rewards patience more than speed. If you slow down before reacting, a conversation may turn in your favour.",
                style = MaterialTheme.typography.bodyLarge,
                color = textPrimary,
                lineHeight = 28.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Affirmation card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(accentFill)
                .border(0.5.dp, accentBorder, RoundedCornerShape(16.dp))
                .padding(18.dp)
        ) {
            Text(
                text = "AFFIRMATION",
                style = MaterialTheme.typography.labelSmall,
                color = primary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "\"I trust myself to respond calmly and clearly.\"",
                style = MaterialTheme.typography.bodyLarge,
                color = primary,
                fontWeight = FontWeight.Medium,
                lineHeight = 28.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Energy row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf(
                Triple("Career", 4, 5),
                Triple("Relations", 3, 5),
                Triple("Focus", 5, 5)
            ).forEach { (label, score, total) ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(surface)
                        .border(0.5.dp, border, RoundedCornerShape(12.dp))
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = textSecondary,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        repeat(total) { index ->
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(if (index < score) primary else border)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Reflect button
        if (!reflected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(primaryDark)
                    .clickable { reflected = true }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "I've reflected on this",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isDark) DarkTextPrimary else LightBackground
                )
            }
        } else {
            // Post-reflection state
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentFill)
                    .border(0.5.dp, accentBorder, RoundedCornerShape(12.dp))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "☽", fontSize = 28.sp, color = primary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "8-day streak",
                    style = MaterialTheme.typography.titleMedium,
                    color = textPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Alignment maintained. See you tomorrow.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}