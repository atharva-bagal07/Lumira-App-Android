package com.example.lumira.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.lumira.MainActivity
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

class NotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val notifNumber = inputData.getInt("notif_number", 1)
        val teaser = inputData.getString("teaser") ?: "Your guidance for today is ready."

        val prefs = UserPreferences(context)
        val today = java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ISO_DATE)
        val lastOpened = prefs.lastOpened.first()

        // Don't send 2nd, 3rd, 4th if already reflected
        if (notifNumber > 1 && lastOpened == today) {
            return Result.success()
        }

        sendNotification(notifNumber, teaser)
        return Result.success()
    }

    private fun sendNotification(notifNumber: Int, teaser: String) {
        val channelId = "lumira_daily"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Daily Guidance",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Your daily Lumira guidance"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Intent to open app on tap
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val (title, body) = when (notifNumber) {
            1 -> Pair("Lumira ✦", "$teaser See why →")
            2 -> Pair("Lumira ✦", "Your guidance is still waiting. Open Lumira →")
            3 -> Pair("Lumira ✦", "Don't miss today's guidance. Read now →")
            4 -> Pair("Lumira ✦", "Your streak is waiting. One minute is enough.")
            else -> Pair("Lumira ✦", teaser)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(notifNumber, notification)
    }
}