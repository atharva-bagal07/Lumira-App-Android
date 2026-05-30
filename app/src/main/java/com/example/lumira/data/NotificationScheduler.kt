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
        workManager.cancelAllWorkByTag("lumira_notif")

        val now = LocalDateTime.now()

        val testTime = if (now.minute + 2 >= 60) {
            now.toLocalDate().atTime(now.hour + 1, (now.minute + 2) - 60)
        } else {
            now.toLocalDate().atTime(now.hour, now.minute + 2)
        }

        // Each time is built with explicit hour and minute — no overflow possible
        val notifTimes = listOf(testTime)

        notifTimes.forEachIndexed { index, scheduledTime ->
            val notifNumber = index + 1
            if (!scheduledTime.isAfter(now)) {
                android.util.Log.d(
                    "LumiraNotif",
                    "Skipping notif #$notifNumber — time already passed: $scheduledTime"
                )
                return@forEachIndexed
            }

            val delayMinutes = now.until(scheduledTime, ChronoUnit.MINUTES)
            android.util.Log.d(
                "LumiraNotif",
                "Scheduling notif #$notifNumber in $delayMinutes minutes at $scheduledTime"
            )

            // rest of enqueue code
            if (delayMinutes <= 0) return@forEachIndexed

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