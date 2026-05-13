package com.example.lumira.ui.onboarding

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
import com.example.lumira.ui.theme.*

data class ZodiacSign(
    val name: String,
    val symbol: String,
    val dates: String
)

val zodiacSigns = listOf(
    ZodiacSign("Aries", "♈", "Mar 21 – Apr 19"),
    ZodiacSign("Taurus", "♉", "Apr 20 – May 20"),
    ZodiacSign("Gemini", "♊", "May 21 – Jun 20"),
    ZodiacSign("Cancer", "♋", "Jun 21 – Jul 22"),
    ZodiacSign("Leo", "♌", "Jul 23 – Aug 22"),
    ZodiacSign("Virgo", "♍", "Aug 23 – Sep 22"),
    ZodiacSign("Libra", "♎", "Sep 23 – Oct 22"),
    ZodiacSign("Scorpio", "♏", "Oct 23 – Nov 21"),
    ZodiacSign("Sagittarius", "♐", "Nov 22 – Dec 21"),
    ZodiacSign("Capricorn", "♑", "Dec 22 – Jan 19"),
    ZodiacSign("Aquarius", "♒", "Jan 20 – Feb 18"),
    ZodiacSign("Pisces", "♓", "Feb 19 – Mar 20"),
)

@Composable
fun ZodiacScreen(onContinue: (String) -> Unit) {
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
        Spacer(modifier = Modifier.height(48.dp))

        ProgressDots(total = 5, current = 2, activeColor = primary, inactiveColor = border)

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "Your zodiac sign",
            style = MaterialTheme.typography.headlineMedium,
            color = textPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "This shapes your daily guidance.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Zodiac grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(zodiacSigns) { sign ->
                val isSelected = selected == sign.name
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isSelected) accentFill else surface)
                        .border(
                            width = if (isSelected) 1.5.dp else 0.5.dp,
                            color = if (isSelected) accentBorder else border,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable { selected = sign.name },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = sign.symbol + "\uFE0E", // \uFE0E forces text rendering, not emoji
                            fontSize = 30.sp,
                            color = if (isSelected) primary else textSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = sign.name,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isSelected) primaryDark else textSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LumiraButton(
            text = "This is my sign",
            onClick = { if (selected.isNotEmpty()) onContinue(selected) },
            backgroundColor = if (selected.isEmpty()) border else primaryDark,
            textColor = if (selected.isEmpty()) textSecondary else if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}