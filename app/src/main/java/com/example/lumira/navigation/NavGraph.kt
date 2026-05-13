package com.example.lumira.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lumira.ui.onboarding.NameScreen
import com.example.lumira.ui.onboarding.WelcomeScreen
import com.example.lumira.ui.onboarding.ZodiacScreen


object Routes {
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

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {
        composable(Routes.WELCOME) {
            WelcomeScreen(onContinue = { navController.navigate(Routes.NAME) })
        }
        composable(Routes.NAME) {
            NameScreen(onContinue = { name ->
                navController.navigate(Routes.ZODIAC)
            })
        }
        composable(Routes.ZODIAC) {
            ZodiacScreen(onContinue = { zodiac ->
                navController.navigate(Routes.NOTIFICATION)
            })
        }
//        composable(Routes.NOTIFICATION) {
//            NotificationScreen(onContinue = { navController.navigate(Routes.JOURNEY) })
//        }
//        composable(Routes.JOURNEY) {
//            JourneyScreen(onContinue = {
//                navController.navigate(Routes.HOME) {
//                    popUpTo(Routes.WELCOME) { inclusive = true }
//                }
//            })
//        }
//        composable(Routes.HOME) {
//            HomeScreen()
//        }
    }
}