package com.example.lumira.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumira.ui.theme.*

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    val isDark = com.example.lumira.ui.theme.isNightTime()

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
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Progress dots
        ProgressDots(total = 5, current = 0, activeColor = primary, inactiveColor = border)

        Spacer(modifier = Modifier.weight(1f))

        // Moon logo
        MoonLogo(primary = primary, accentFill = accentFill)

        Spacer(modifier = Modifier.height(28.dp))

        // App name
        Text(
            text = "Lumira",
            style = MaterialTheme.typography.displayLarge,
            color = textPrimary,

            )

        Spacer(modifier = Modifier.height(10.dp))

        // Tagline
        Text(
            text = "Your daily light arrives every morning.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        // Primary button
        LumiraButton(
            text = "Begin your journey",
            onClick = onContinue,
            backgroundColor = primaryDark,
            textColor = if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Ghost button
        LumiraGhostButton(
            text = "I already have an account",
            onClick = { },
            textColor = textSecondary,
            borderColor = border
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}