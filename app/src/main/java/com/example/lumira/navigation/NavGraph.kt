package com.example.lumira.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lumira.ui.onboarding.*
import com.example.lumira.ui.home.HomeScreen
import com.example.lumira.viewmodel.LumiraViewModel

object Routes {
    const val SPLASH = "splash"
    const val WELCOME = "welcome"
    const val NAME = "name"
    const val ZODIAC = "zodiac"
    const val NOTIFICATION = "notification"
    const val JOURNEY = "journey"
    const val HOME = "home"
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val viewModel: LumiraViewModel = viewModel()
    val context = LocalContext.current

    val onboardingDone by viewModel.onboardingDone.collectAsState()
    val isReady by viewModel.isReady.collectAsState()

    if (!isReady) {
        // Show nothing while DataStore loads
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
                viewModel.saveName(name)
                navController.navigate(Routes.ZODIAC)
            })
        }
        composable(Routes.ZODIAC) {
            ZodiacScreen(onContinue = { zodiac ->
                viewModel.saveZodiac(zodiac)
                navController.navigate(Routes.NOTIFICATION)
            })
        }
        composable(Routes.NOTIFICATION) {
            NotificationScreen(onContinue = { hour ->
                viewModel.saveNotifHour(hour)
                navController.navigate(Routes.JOURNEY)
            })
        }
        composable(Routes.JOURNEY) {
            JourneyScreen(onContinue = {
                viewModel.completeOnboarding()
                viewModel.scheduleNotifications(
                    context,
                    "Today brings unexpected clarity."
                )
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.WELCOME) { inclusive = true }
                }
            })
        }
        composable(Routes.HOME) {
            HomeScreen(viewModel = viewModel)
        }
    }
}