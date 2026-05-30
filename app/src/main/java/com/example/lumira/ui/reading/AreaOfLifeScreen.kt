package com.example.lumira.ui.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.lumira.viewmodel.LumiraViewModel
import com.example.lumira.viewmodel.ReadingViewModel

data class AreaOfLife(
    val name: String,
    val symbol: String
)

val areasOfLife = listOf(
    AreaOfLife("Love", "☽"),
    AreaOfLife("Career", "↑"),
    AreaOfLife("Health", "◉"),
    AreaOfLife("Money", "◎"),
    AreaOfLife("Family", "✦"),
    AreaOfLife("Friendship", "∞"),
    AreaOfLife("Creativity", "◈"),
    AreaOfLife("Purpose", "☆"),
)

@Composable
fun AreaOfLifeScreen(
    lumiraViewModel: LumiraViewModel,
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

    val usedAreasToday by lumiraViewModel.usedAreasToday.collectAsState()
    val usedAreasList = usedAreasToday.split(",").filter { it.isNotEmpty() }

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
                text = "Area of life",
                style = MaterialTheme.typography.titleMedium,
                color = textPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "What would you like guidance on today?",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        if (usedAreasList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Areas you've already explored today are greyed out.",
                style = MaterialTheme.typography.bodyMedium,
                color = textSecondary,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(areasOfLife) { area ->
                val isUsed = usedAreasList.contains(area.name)
                val isSelected = selected == area.name

                Box(
                    modifier = Modifier
                        .aspectRatio(1.6f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            when {
                                isSelected -> accentFill
                                isUsed -> if (isDark) DarkSurface else LightBorder
                                else -> surface
                            }
                        )
                        .border(
                            width = if (isSelected) 1.5.dp else 0.5.dp,
                            color = when {
                                isSelected -> accentBorder
                                isUsed -> border
                                else -> border
                            },
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable(enabled = !isUsed) {
                            selected = area.name
                        }
                        .padding(12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        Text(
                            text = area.symbol,
                            fontSize = 18.sp,
                            color = when {
                                isSelected -> primary
                                isUsed -> textSecondary.copy(alpha = 0.4f)
                                else -> textSecondary
                            }
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = area.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = when {
                                isSelected -> primary
                                isUsed -> textSecondary.copy(alpha = 0.4f)
                                else -> textPrimary
                            },
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LumiraButton(
            text = "Continue",
            onClick = {
                if (selected.isNotEmpty()) {
                    readingViewModel.setArea(selected)
                    onContinue()
                }
            },
            backgroundColor = if (selected.isEmpty()) border else primaryDark,
            textColor = if (selected.isEmpty()) textSecondary else if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}