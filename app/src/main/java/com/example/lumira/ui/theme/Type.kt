package com.example.lumira.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lumira.R

val CormorantGaramond = FontFamily(
    Font(R.font.cormorant_regular, FontWeight.Normal),
    Font(R.font.cormorant_medium, FontWeight.Medium),
    Font(R.font.cormorant_semibold, FontWeight.SemiBold),
    Font(R.font.cormorant_bold, FontWeight.Bold)
)
val Typography = Typography(

    // App title — "Lumira" wordmark
    displayLarge = TextStyle(
        fontFamily = CormorantGaramond,
        fontWeight = FontWeight.Bold,
        fontSize = 72.sp,
        lineHeight = 80.sp,
        letterSpacing = 0.sp
    ),

    // Screen headings — "What should we call you?"
    headlineMedium = TextStyle(
        fontFamily = CormorantGaramond,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),

    // Card titles
    titleMedium = TextStyle(
        fontFamily = CormorantGaramond,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Guidance card body text
    bodyLarge = TextStyle(
        fontFamily = CormorantGaramond,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Secondary body — subtitles, hints
    bodyMedium = TextStyle(
        fontFamily = CormorantGaramond,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Labels — "TODAY'S GUIDANCE", "AFFIRMATION"
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.08.sp
    ),

    // Buttons, pills — kept as default for clarity
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    )
)