package com.example.lumira.ui.reading

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumira.data.AstronomyEngine
import kotlinx.coroutines.launch
import com.example.lumira.data.GeminiRepository
import com.example.lumira.model.ReadingSession
import com.example.lumira.ui.theme.*
import com.example.lumira.viewmodel.LumiraViewModel
import com.example.lumira.viewmodel.ReadingViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReadingLoadingScreen(
    lumiraViewModel: LumiraViewModel,
    readingViewModel: ReadingViewModel,
    onReadingReady: () -> Unit,
    onError: () -> Unit
) {
    val isDark = isNightTime()
    val bg = if (isDark) DarkBackground else LightBackground
    val textPrimary = if (isDark) DarkTextPrimary else LightTextPrimary
    val textSecondary = if (isDark) DarkTextSecondary else LightTextSecondary
    val primary = if (isDark) DarkPrimary else LightPrimary

    val name by lumiraViewModel.userName.collectAsState()
    val zodiac by lumiraViewModel.userZodiac.collectAsState()
    val chineseZodiac by lumiraViewModel.userChineseZodiac.collectAsState()
    val lifePathNumber by lumiraViewModel.lifePathNumber.collectAsState()
    val lifePhase by lumiraViewModel.lifePhase.collectAsState()
    val birthTime by lumiraViewModel.userBirthTime.collectAsState()

    val selectedStyle by readingViewModel.selectedStyle.collectAsState()
    val selectedArea by readingViewModel.selectedArea.collectAsState()
    val selectedMood by readingViewModel.selectedMood.collectAsState()
    val question by readingViewModel.question.collectAsState()

    var hasStarted by remember { mutableStateOf(false) }


    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    val loadingMessages = listOf(
        "Reading the stars...",
        "Consulting the cosmos...",
        "Weaving your guidance...",
        "Aligning the energy..."
    )
    var messageIndex by remember { mutableStateOf(0) }

    LaunchedEffect(zodiac, selectedStyle, question) {
        if (hasStarted) return@LaunchedEffect
        if (zodiac.isEmpty() || selectedStyle.isEmpty() || question.isEmpty()) return@LaunchedEffect

        hasStarted = true

        android.util.Log.d(
            "LumiraReading",
            "generateReading called — zodiac: $zodiac, style: $selectedStyle"
        )

        val repository = GeminiRepository()
        val astrologyContext = AstronomyEngine.getAstrologyContext()
        val personalDay = lumiraViewModel.getPersonalDayNumber()

        val messageJob = launch {
            while (true) {
                kotlinx.coroutines.delay(1500)
                messageIndex = (messageIndex + 1) % loadingMessages.size
            }
        }

        try {
            android.util.Log.d("LumiraReading", "Calling Gemini API now...")
            val result = repository.generateReading(
                name = name,
                zodiacSign = zodiac,
                lifePhase = lifePhase,
                readingStyle = selectedStyle,
                areaOfLife = selectedArea,
                mood = selectedMood,
                question = question
            )
            messageJob.cancel()
            result.fold(
                onSuccess = { reading ->
                    android.util.Log.d("LumiraReading", "Reading received successfully")
                    val session = ReadingSession(
                        id = UUID.randomUUID().toString(),
                        date = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                        readingStyle = selectedStyle,
                        areaOfLife = selectedArea,
                        mood = selectedMood,
                        question = question,
                        response = reading,
                        zodiacSign = zodiac,
                        timestamp = System.currentTimeMillis()
                    )
                    readingViewModel.setReadingResult(reading)
                    lumiraViewModel.saveReadingSession(session)
                    onReadingReady()
                },
                onFailure = { error ->
                    android.util.Log.e("LumiraReading", "Reading failed: ${error.message}")
                    readingViewModel.setError(error.message ?: "Something went wrong.")
                    onError()
                }
            )
        } catch (e: Exception) {
            messageJob.cancel()
            android.util.Log.e("LumiraReading", "Exception: ${e.message}")
            readingViewModel.setError(e.message ?: "Something went wrong.")
            onError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "☽",
            fontSize = 64.sp,
            color = primary,
            modifier = Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = loadingMessages[messageIndex],
            style = MaterialTheme.typography.titleMedium,
            color = textPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Preparing your $selectedStyle reading\non $selectedArea",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
    }
}