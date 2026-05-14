package com.example.lumira.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lumira.R

val Almendra = FontFamily(
    Font(R.font.almendra_regular, FontWeight.Normal),
    Font(R.font.almendra_bold, FontWeight.Bold)
)

val Typography = Typography(

    // App title — "Lumira" wordmark
    displayLarge = TextStyle(
        fontFamily = Almendra,
        fontWeight = FontWeight.Bold,
        fontSize = 52.sp,
        lineHeight = 60.sp,
        letterSpacing = 2.sp
    ),

    // Screen headings
    headlineMedium = TextStyle(
        fontFamily = Almendra,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),

    // Card titles
    titleMedium = TextStyle(
        fontFamily = Almendra,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Guidance card body text
    bodyLarge = TextStyle(
        fontFamily = Almendra,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Secondary body — subtitles, hints
    bodyMedium = TextStyle(
        fontFamily = Almendra,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),

    // Labels — kept as default for clarity at small sizes
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.08.sp
    ),

    // Buttons, pills
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    )
)