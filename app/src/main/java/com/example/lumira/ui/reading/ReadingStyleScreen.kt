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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumira.ui.onboarding.LumiraButton
import com.example.lumira.ui.onboarding.LumiraGhostButton
import com.example.lumira.ui.theme.*
import com.example.lumira.viewmodel.ReadingViewModel

data class ReadingStyle(
    val name: String,
    val symbol: String,
    val description: String
)

val readingStyles = listOf(
    ReadingStyle("Horoscope", "☽", "Classic star sign guidance"),
    ReadingStyle("Tarot", "✦", "Card energy for today"),
    ReadingStyle("Numerology", "◎", "Numbers reveal your path"),
    ReadingStyle("Moon Energy", "◉", "Lunar phase influence"),
    ReadingStyle("Vedic", "∞", "Ancient Indian astrology"),
)

@Composable
fun ReadingStyleScreen(
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
                text = "Choose a reading style",
                style = MaterialTheme.typography.titleMedium,
                color = textPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "How would you like your guidance today?",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        readingStyles.forEach { style ->
            val isSelected = selected == style.name
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
                    .clickable { selected = style.name }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = style.symbol,
                    fontSize = 22.sp,
                    color = if (isSelected) primary else textSecondary,
                    modifier = Modifier.width(36.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = style.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSelected) primary else textPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = style.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSelected) primary else textSecondary,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        LumiraButton(
            text = "Continue",
            onClick = {
                if (selected.isNotEmpty()) {
                    readingViewModel.setStyle(selected)
                    onContinue()
                }
            },
            backgroundColor = if (selected.isEmpty()) border else primaryDark,
            textColor = if (selected.isEmpty()) textSecondary else if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}