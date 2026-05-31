package com.example.lumira.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumira.ui.theme.*
import com.example.lumira.viewmodel.LumiraViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: LumiraViewModel,
    onStartReading: () -> Unit,
    onViewHistory: () -> Unit
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

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val zodiac by viewModel.userZodiac.collectAsState()
    val name by viewModel.userName.collectAsState()
    val streak by viewModel.streak.collectAsState()
    val dailyGuidance by viewModel.dailyGuidance.collectAsState()
    val readingsToday by viewModel.readingsToday.collectAsState()
    val subscriptionTier by viewModel.subscriptionTier.collectAsState()

    val maxReadings = if (subscriptionTier == "premium") 3 else 1
    val canRead = readingsToday < maxReadings

    var reflected by remember { mutableStateOf(false) }
    var animateTrigger by remember { mutableStateOf(false) }

    val streakScale by animateFloatAsState(
        targetValue = if (animateTrigger) 1.4f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        finishedListener = { animateTrigger = false },
        label = "streakScale"
    )

    LaunchedEffect(zodiac) {
        if (zodiac.isNotEmpty()) {
            viewModel.checkAndUpdateStreak()
            viewModel.fetchDailyGuidance(zodiac)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (name.isNotEmpty()) "Hello, $name" else zodiac,
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.graphicsLayer {
                    scaleX = streakScale
                    scaleY = streakScale
                }
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

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = zodiac,
                style = MaterialTheme.typography.bodyMedium,
                color = primary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$readingsToday/$maxReadings readings today",
                style = MaterialTheme.typography.bodyMedium,
                color = textSecondary,
                fontSize = 12.sp,
                modifier = Modifier.clickable { onViewHistory() }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(surface)
                .border(0.5.dp, border, RoundedCornerShape(20.dp))
                .padding(24.dp)
        ) {
            Text(
                text = "TODAY'S GUIDANCE",
                style = MaterialTheme.typography.labelSmall,
                color = textSecondary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = dailyGuidance.ifEmpty { "Loading your guidance..." },
                style = MaterialTheme.typography.bodyLarge,
                color = textPrimary,
                lineHeight = 32.sp,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (!reflected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(surface)
                    .border(0.5.dp, border, RoundedCornerShape(14.dp))
                    .clickable {
                        scope.launch {
                            viewModel.markReflected()
                            delay(300)
                            reflected = true
                            animateTrigger = true
                            viewModel.cancelNotifications(context)
                        }
                    }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "I've read today's guidance",
                    style = MaterialTheme.typography.labelMedium,
                    color = textSecondary
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(accentFill)
                    .border(0.5.dp, accentBorder, RoundedCornerShape(14.dp))
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔥 $streak day streak — see you tomorrow",
                    style = MaterialTheme.typography.labelMedium,
                    color = primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(if (canRead) primaryDark else accentFill)
                .border(
                    0.5.dp,
                    if (canRead) primaryDark else accentBorder,
                    RoundedCornerShape(14.dp)
                )
                .clickable { if (canRead) onStartReading() }
                .padding(vertical = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (canRead) "Get your reading" else "No readings left today",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (canRead) {
                        if (isDark) DarkTextPrimary else LightBackground
                    } else primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                if (!canRead && subscriptionTier != "premium") {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Upgrade to Oracle for 3 readings per day",
                        style = MaterialTheme.typography.bodyMedium,
                        color = primary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}