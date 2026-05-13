package com.example.lumira.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// ─── Shared Components ───────────────────────────────────

@Composable
fun ProgressDots(total: Int, current: Int, activeColor: Color, inactiveColor: Color) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        repeat(total) { index ->
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(if (index <= current) activeColor else inactiveColor)
            )
        }
    }
}

@Composable
fun MoonLogo(primary: Color, accentFill: Color) {
    Box(
        modifier = Modifier.size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer ring
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
                .padding(2.dp)
        )
        // Moon crescent using overlapping circles
        Box(modifier = Modifier.size(72.dp)) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(accentFill)
                    .align(Alignment.Center)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(primary.copy(alpha = 0.15f))
                    .align(Alignment.CenterEnd)
            )
        }
        // Center dot
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(primary)
                .align(Alignment.TopStart)
                .offset(x = 14.dp, y = 14.dp)
        )
    }
}

@Composable
fun LumiraButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier

            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .then(
                Modifier.padding(vertical = 14.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun LumiraGhostButton(
    text: String,
    onClick: () -> Unit,
    textColor: Color,
    borderColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent)
            .padding(vertical = 13.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor
        )
    }
}