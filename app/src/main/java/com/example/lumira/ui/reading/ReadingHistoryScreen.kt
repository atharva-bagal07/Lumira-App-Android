package com.example.lumira.ui.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumira.model.ReadingSession
import com.example.lumira.ui.theme.*
import com.example.lumira.viewmodel.LumiraViewModel

@Composable
fun ReadingHistoryScreen(
    lumiraViewModel: LumiraViewModel,
    onBack: () -> Unit
) {
    val isDark = isNightTime()
    val bg = if (isDark) DarkBackground else LightBackground
    val textPrimary = if (isDark) DarkTextPrimary else LightTextPrimary
    val textSecondary = if (isDark) DarkTextSecondary else LightTextSecondary
    val primary = if (isDark) DarkPrimary else LightPrimary
    val border = if (isDark) DarkBorder else LightBorder
    val surface = if (isDark) DarkSurface else LightSurface
    val accentFill = if (isDark) DarkAccentFill else LightAccentFill
    val accentBorder = if (isDark) DarkAccentBorder else LightAccentBorder

    val zodiac by lumiraViewModel.userZodiac.collectAsState()
    val history by lumiraViewModel.readingHistory.collectAsState()
    val subscriptionTier by lumiraViewModel.subscriptionTier.collectAsState()

    var expandedId by remember { mutableStateOf("") }

    LaunchedEffect(zodiac) {
        if (zodiac.isNotEmpty()) {
            lumiraViewModel.fetchReadingHistory(zodiac)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
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
                text = "Reading History",
                style = MaterialTheme.typography.titleMedium,
                color = textPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (subscriptionTier != "premium") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentFill)
                    .border(0.5.dp, accentBorder, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "Upgrade to Oracle to access your full reading history.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = primary,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (history.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "☽",
                        fontSize = 48.sp,
                        color = primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No readings yet.",
                        style = MaterialTheme.typography.titleMedium,
                        color = textPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Your readings will appear here.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = textSecondary
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(history) { session ->
                    val isExpanded = expandedId == session.id
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(surface)
                            .border(0.5.dp, border, RoundedCornerShape(14.dp))
                            .clickable {
                                expandedId = if (isExpanded) "" else session.id
                            }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${session.readingStyle} · ${session.areaOfLife}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = session.date,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = textSecondary,
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = if (isExpanded) "↑" else "↓",
                                fontSize = 16.sp,
                                color = textSecondary
                            )
                        }

                        if (!isExpanded) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = session.question,
                                style = MaterialTheme.typography.bodyMedium,
                                color = textSecondary,
                                fontSize = 13.sp,
                                maxLines = 1
                            )
                        }

                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(14.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(accentFill)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = session.question,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = primary,
                                    fontSize = 13.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = session.response,
                                style = MaterialTheme.typography.bodyMedium,
                                color = textPrimary,
                                fontSize = 14.sp,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}