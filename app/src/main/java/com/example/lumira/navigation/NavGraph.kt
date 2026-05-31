package com.example.lumira.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lumira.ui.home.HomeScreen
import com.example.lumira.ui.onboarding.BirthTimeScreen
import com.example.lumira.ui.onboarding.DobScreen
import com.example.lumira.ui.onboarding.JourneyScreen
import com.example.lumira.ui.onboarding.LifePhaseScreen
import com.example.lumira.ui.onboarding.NameScreen
import com.example.lumira.ui.onboarding.NotificationScreen
import com.example.lumira.ui.onboarding.PaywallScreen
import com.example.lumira.ui.onboarding.WelcomeScreen
import com.example.lumira.ui.reading.AreaOfLifeScreen
import com.example.lumira.ui.reading.MoodScreen
import com.example.lumira.ui.reading.QuestionScreen
import com.example.lumira.ui.reading.ReadingHistoryScreen
import com.example.lumira.ui.reading.ReadingLoadingScreen
import com.example.lumira.ui.reading.ReadingResultScreen
import com.example.lumira.ui.reading.ReadingStyleScreen
import com.example.lumira.ui.theme.DarkBackground
import com.example.lumira.ui.theme.LightBackground
import com.example.lumira.ui.theme.isNightTime
import com.example.lumira.viewmodel.LumiraViewModel
import com.example.lumira.viewmodel.ReadingViewModel

object Routes {
    const val WELCOME = "welcome"
    const val NAME = "name"
    const val DOB = "dob"
    const val BIRTH_TIME = "birth_time"
    const val LIFE_PHASE = "life_phase"
    const val NOTIFICATION = "notification"
    const val JOURNEY = "journey"
    const val PAYWALL = "paywall"
    const val HOME = "home"
    const val READING_STYLE = "reading_style"
    const val AREA_OF_LIFE = "area_of_life"
    const val MOOD = "mood"
    const val QUESTION = "question"
    const val READING_LOADING = "reading_loading"
    const val READING_RESULT = "reading_result"
    const val READING_HISTORY = "reading_history"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val lumiraViewModel: LumiraViewModel = viewModel()
    val readingViewModel: ReadingViewModel = viewModel()
    val context = LocalContext.current

    val onboardingDone by lumiraViewModel.onboardingDone.collectAsState()
    val isReady by lumiraViewModel.isReady.collectAsState()

    if (!isReady) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isNightTime()) DarkBackground else LightBackground
                )
        )
        return
    }

    NavHost(
        navController = navController,
        startDestination = if (onboardingDone) Routes.HOME else Routes.WELCOME
    ) {
        composable(Routes.WELCOME) {
            WelcomeScreen(onContinue = { navController.navigate(Routes.NAME) })
        }
        composable(Routes.NAME) {
            NameScreen(onContinue = { name ->
                lumiraViewModel.saveName(name)
                navController.navigate(Routes.DOB)
            })
        }
        composable(Routes.DOB) {
            DobScreen(onContinue = { dob, zodiac, chineseZodiac, lifePath ->
                lumiraViewModel.saveDob(dob, zodiac, chineseZodiac, lifePath)
                navController.navigate(Routes.BIRTH_TIME)
            })
        }
        composable(Routes.BIRTH_TIME) {
            BirthTimeScreen(onContinue = { birthTime ->
                lumiraViewModel.saveBirthTime(birthTime)
                navController.navigate(Routes.LIFE_PHASE)
            })
        }
        composable(Routes.LIFE_PHASE) {
            LifePhaseScreen(onContinue = { phase ->
                lumiraViewModel.saveLifePhase(phase)
                navController.navigate(Routes.NOTIFICATION)
            })
        }
        composable(Routes.NOTIFICATION) {
            NotificationScreen(onContinue = { hour ->
                lumiraViewModel.saveNotifHour(hour)
                navController.navigate(Routes.JOURNEY)
            })
        }
        composable(Routes.JOURNEY) {
            JourneyScreen(onContinue = {
                navController.navigate(Routes.PAYWALL)
            })
        }
        composable(Routes.PAYWALL) {
            PaywallScreen(
                onSubscribe = { tier ->
                    lumiraViewModel.saveSubscription(tier)
                    lumiraViewModel.completeOnboarding()
                    lumiraViewModel.scheduleNotifications(
                        context,
                        "Your guidance for today is ready."
                    )
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.WELCOME) { inclusive = true }
                    }
                },
                onSkip = {
                    lumiraViewModel.completeOnboarding()
                    lumiraViewModel.scheduleNotifications(
                        context,
                        "Your guidance for today is ready."
                    )
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.WELCOME) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = lumiraViewModel,
                onStartReading = { navController.navigate(Routes.READING_STYLE) }
            ) { navController.navigate(Routes.READING_HISTORY) }
        }
        composable(Routes.READING_STYLE) {
            ReadingStyleScreen(
                readingViewModel = readingViewModel,
                onContinue = { navController.navigate(Routes.AREA_OF_LIFE) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.AREA_OF_LIFE) {
            AreaOfLifeScreen(
                lumiraViewModel = lumiraViewModel,
                readingViewModel = readingViewModel,
                onContinue = { navController.navigate(Routes.MOOD) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.MOOD) {
            MoodScreen(
                readingViewModel = readingViewModel,
                onContinue = { navController.navigate(Routes.QUESTION) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.QUESTION) {
            QuestionScreen(
                readingViewModel = readingViewModel,
                onContinue = { navController.navigate(Routes.READING_LOADING) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.READING_LOADING) {
            ReadingLoadingScreen(
                lumiraViewModel = lumiraViewModel,
                readingViewModel = readingViewModel,
                onReadingReady = {
                    navController.navigate(Routes.READING_RESULT) {
                        popUpTo(Routes.READING_LOADING) { inclusive = true }
                    }
                },
                onError = { navController.popBackStack() }
            )
        }
        composable(Routes.READING_RESULT) {
            ReadingResultScreen(
                lumiraViewModel = lumiraViewModel,
                readingViewModel = readingViewModel,
                onDone = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onAnotherReading = {
                    navController.navigate(Routes.READING_STYLE) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                }
            )
        }
        composable(Routes.READING_HISTORY) {
            ReadingHistoryScreen(
                lumiraViewModel = lumiraViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}