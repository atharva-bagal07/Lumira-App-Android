package com.example.lumira.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lumira.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthTimeScreen(onContinue: (String) -> Unit) {
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

    val hours = (1..12).toList()
    val amPm = listOf("AM", "PM")

    var selectedHour by remember { mutableStateOf(6) }
    var selectedAmPm by remember { mutableStateOf("AM") }
    var hourExpanded by remember { mutableStateOf(false) }
    var amPmExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        ProgressDots(total = 8, current = 3, activeColor = primary, inactiveColor = border)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "What time were you born?",
            style = MaterialTheme.typography.headlineMedium,
            color = textPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "This helps refine your reading. You can skip if you don't know.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(surface)
                        .border(
                            0.5.dp,
                            if (hourExpanded) primary else border,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { hourExpanded = true }
                        .padding(16.dp)
                ) {
                    Text(
                        text = "$selectedHour:00",
                        style = MaterialTheme.typography.bodyMedium,
                        color = textPrimary
                    )
                }
                DropdownMenu(
                    expanded = hourExpanded,
                    onDismissRequest = { hourExpanded = false },
                    modifier = Modifier.background(surface)
                ) {
                    hours.forEach { hour ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "$hour:00",
                                    color = if (hour == selectedHour) primary else textPrimary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = { selectedHour = hour; hourExpanded = false }
                        )
                    }
                }
//                ExposedDropdownMenuBox(
//                    expanded = hourExpanded,
//                    onExpandedChange = { hourExpanded = !hourExpanded }
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .menuAnchor()
//                            .clip(RoundedCornerShape(12.dp))
//                            .background(surface)
//                            .border(0.5.dp, if (hourExpanded) primary else border, RoundedCornerShape(12.dp))
//                            .padding(14.dp)
//                    ) {
//                        Text(
//                            text = selectedHour.toString(),
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = textPrimary
//                        )
//                    }
//                    ExposedDropdownMenu(
//                        expanded = hourExpanded,
//                        onDismissRequest = { hourExpanded = false },
//                        modifier = Modifier.background(surface)
//                    ) {
//                        hours.forEach { hour ->
//                            DropdownMenuItem(
//                                text = {
//                                    Text(
//                                        hour.toString(),
//                                        color = if (hour == selectedHour) primary else textPrimary,
//                                        style = MaterialTheme.typography.bodyMedium
//                                    )
//                                },
//                                onClick = { selectedHour = hour; hourExpanded = false }
//                            )
//                        }
//                    }
//                }
            }

            Box(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(surface)
                        .border(
                            0.5.dp,
                            if (amPmExpanded) primary else border,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { amPmExpanded = true }
                        .padding(16.dp)
                ) {
                    Text(
                        text = selectedAmPm,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textPrimary
                    )
                }
                DropdownMenu(
                    expanded = amPmExpanded,
                    onDismissRequest = { amPmExpanded = false },
                    modifier = Modifier.background(surface)
                ) {
                    amPm.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    option,
                                    color = if (option == selectedAmPm) primary else textPrimary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = { selectedAmPm = option; amPmExpanded = false }
                        )
                    }
                }
//                ExposedDropdownMenuBox(
//                    expanded = amPmExpanded,
//                    onExpandedChange = { amPmExpanded = !amPmExpanded }
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .menuAnchor()
//                            .clip(RoundedCornerShape(12.dp))
//                            .background(surface)
//                            .border(0.5.dp, if (amPmExpanded) primary else border, RoundedCornerShape(12.dp))
//                            .padding(14.dp)
//                    ) {
//                        Text(
//                            text = selectedAmPm,
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = textPrimary
//                        )
//                    }
//                    ExposedDropdownMenu(
//                        expanded = amPmExpanded,
//                        onDismissRequest = { amPmExpanded = false },
//                        modifier = Modifier.background(surface)
//                    ) {
//                        amPm.forEach { option ->
//                            DropdownMenuItem(
//                                text = {
//                                    Text(
//                                        option,
//                                        color = if (option == selectedAmPm) primary else textPrimary,
//                                        style = MaterialTheme.typography.bodyMedium
//                                    )
//                                },
//                                onClick = { selectedAmPm = option; amPmExpanded = false }
//                            )
//                        }
//                    }
//                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        LumiraButton(
            text = "Continue",
            onClick = { onContinue("$selectedHour:00 $selectedAmPm") },
            backgroundColor = primaryDark,
            textColor = if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        LumiraGhostButton(
            text = "I don't know my birth time",
            onClick = { onContinue("") },
            textColor = textSecondary,
            borderColor = border
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}