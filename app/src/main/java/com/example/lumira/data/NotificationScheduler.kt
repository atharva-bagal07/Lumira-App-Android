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

        val notifTimes = listOf(
            now.withHour(8).withMinute(Random.nextInt(0, 59)).withSecond(0),
            now.withHour(14).withMinute(Random.nextInt(0, 59)).withSecond(0),
            now.withHour(Random.nextInt(18, 19)).withMinute(Random.nextInt(0, 59)).withSecond(0),
            now.withHour(Random.nextInt(21, 22)).withMinute(Random.nextInt(0, 59)).withSecond(0),
        )

        notifTimes.forEachIndexed { index, scheduledTime ->
            val notifNumber = index + 1
            if (scheduledTime.isBefore(now)) return@forEachIndexed

            val delayMinutes = now.until(scheduledTime, ChronoUnit.MINUTES)
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