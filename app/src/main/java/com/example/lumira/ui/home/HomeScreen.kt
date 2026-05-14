package com.example.lumira.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.example.lumira.ui.theme.*
import com.example.lumira.viewmodel.LumiraViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: LumiraViewModel) {
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

    val zodiac by viewModel.userZodiac.collectAsState()
    val streak by viewModel.streak.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkAndUpdateStreak()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
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
                    text = zodiac.ifEmpty { "—" },
                    style = MaterialTheme.typography.titleMedium,
                    color = textPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = java.time.LocalDate.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMM d")),
                    fontSize = 13.sp,
                    color = textSecondary
                )
            }

            // Streak pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "🔥", fontSize = 20.sp)
                Text(
                    text = streak.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        // Guidance card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(surface)
                .border(0.5.dp, border, RoundedCornerShape(20.dp))
                .padding(26.dp)
        ) {
            Text(
                text = "TODAY'S GUIDANCE",
                style = MaterialTheme.typography.labelSmall,
                color = textSecondary,
                letterSpacing = 1.sp,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Today rewards patience more than speed. If you slow down before reacting, a conversation may turn in your favour.",
                style = MaterialTheme.typography.bodyLarge,
                color = textPrimary,
                lineHeight = 36.sp,
                fontSize = 26.sp
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))

        // Reflect button or confirmed state
        if (!reflected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(primaryDark)
                    .clickable {
                        reflected = true
                        viewModel.markReflected()
                    }
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "I've reflected on this",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isDark) DarkTextPrimary else LightBackground
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(accentFill)
                    .border(0.5.dp, accentBorder, RoundedCornerShape(14.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "☽", fontSize = 32.sp, color = primary)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$streak-day streak",
                    style = MaterialTheme.typography.titleMedium,
                    color = textPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Alignment maintained. See you tomorrow.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}