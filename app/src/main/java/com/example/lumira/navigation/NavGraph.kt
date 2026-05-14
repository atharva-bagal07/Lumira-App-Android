package com.example.lumira.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lumira.ui.home.HomeScreen
import com.example.lumira.ui.onboarding.JourneyScreen
import com.example.lumira.ui.onboarding.NameScreen
import com.example.lumira.ui.onboarding.NotificationScreen
import com.example.lumira.ui.onboarding.WelcomeScreen
import com.example.lumira.ui.onboarding.ZodiacScreen
import com.example.lumira.viewmodel.LumiraViewModel


object Routes {
    const val WELCOME = "welcome"
    const val NAME = "name"
    const val ZODIAC = "zodiac"
    const val NOTIFICATION = "notification"
    const val JOURNEY = "journey"
    const val HOME = "home"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val viewModel: LumiraViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
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