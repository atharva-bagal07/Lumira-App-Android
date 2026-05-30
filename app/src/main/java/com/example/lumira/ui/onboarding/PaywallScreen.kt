package com.example.lumira.ui.onboarding

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

@Composable
fun PaywallScreen(
    onSubscribe: (String) -> Unit,
    onSkip: () -> Unit
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

    var selectedPlan by remember { mutableStateOf("yearly") }

    val features = listOf(
        "3 AI readings per day" to "1 reading per day",
        "All reading styles" to "Horoscope only",
        "Full reading history" to "Last reading only",
        "No limits on areas of life" to "All areas available",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Text(
            text = "Unlock Oracle",
            style = MaterialTheme.typography.headlineMedium,
            color = textPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Personalised AI readings every day.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(surface)
                .border(0.5.dp, border, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Oracle",
                    style = MaterialTheme.typography.bodyMedium,
                    color = primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Seeker",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            features.forEach { (premium, free) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "✦ $premium",
                        style = MaterialTheme.typography.bodyMedium,
                        color = primary,
                        fontSize = 13.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = free,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textSecondary,
                        fontSize = 13.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (selectedPlan == "yearly") accentFill else surface)
                    .border(
                        width = if (selectedPlan == "yearly") 1.5.dp else 0.5.dp,
                        color = if (selectedPlan == "yearly") accentBorder else border,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { selectedPlan = "yearly" }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(99.dp))
                            .background(primary)
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "BEST VALUE",
                            fontSize = 9.sp,
                            color = bg,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "₹499",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (selectedPlan == "yearly") primary else textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "per year",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedPlan == "yearly") primary else textSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "≈ ₹41/month",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedPlan == "yearly") primary else textSecondary,
                        fontSize = 11.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (selectedPlan == "monthly") accentFill else surface)
                    .border(
                        width = if (selectedPlan == "monthly") 1.5.dp else 0.5.dp,
                        color = if (selectedPlan == "monthly") accentBorder else border,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { selectedPlan = "monthly" }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(21.dp))
                    Text(
                        text = "₹99",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (selectedPlan == "monthly") primary else textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "per month",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedPlan == "monthly") primary else textSecondary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LumiraButton(
            text = "Start Oracle — ${if (selectedPlan == "yearly") "₹499/year" else "₹99/month"}",
            onClick = { onSubscribe("premium") },
            backgroundColor = primaryDark,
            textColor = if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Continue as Seeker — free forever",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            fontSize = 13.sp,
            modifier = Modifier
                .clickable { onSkip() }
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cancel anytime. No hidden charges.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}