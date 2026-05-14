package com.example.lumira.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import kotlin.random.Random

object NotificationScheduler {

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleDailyNotifications(context: Context, teaser: String) {
        val workManager = WorkManager.getInstance(context)

        // Cancel any existing notifications
        workManager.cancelAllWorkByTag("lumira_notif")

        val now = LocalDateTime.now()

        // Define notification times with random minutes in window
        val notifTimes = listOf(
            // 1st: 8-9 AM
            LocalDateTime.now().withHour(8)
                .withMinute(Random.nextInt(0, 60))
                .withSecond(0),
            // 2nd: 2 PM
            LocalDateTime.now().withHour(14)
                .withMinute(Random.nextInt(0, 30))
                .withSecond(0),
            // 3rd: 6-8 PM
            LocalDateTime.now().withHour(18)
                .withMinute(Random.nextInt(0, 120))
                .withSecond(0),
            // 4th: 9-11 PM
            LocalDateTime.now().withHour(21)
                .withMinute(Random.nextInt(0, 120))
                .withSecond(0),
        )

        notifTimes.forEachIndexed { index, scheduledTime ->
            val notifNumber = index + 1

            // Skip if time has already passed today
            if (scheduledTime.isBefore(now)) return@forEachIndexed

            val delayMinutes = now.until(scheduledTime, ChronoUnit.MINUTES)

            val inputData = workDataOf(
                "notif_number" to notifNumber,
                "teaser" to teaser
            )

            val request = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
                .setInputData(inputData)
                .addTag("lumira_notif")
                .build()

            workManager.enqueue(request)
        }
    }

    fun cancelAll(context: Context) {
        WorkManager.getInstance(context).cancelAllWorkByTag("lumira_notif")
    }
}