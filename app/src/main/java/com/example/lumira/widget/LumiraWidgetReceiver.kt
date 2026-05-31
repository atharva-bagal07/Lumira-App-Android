package com.example.lumira.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.example.lumira.R
import com.example.lumira.data.FirestoreRepository
import com.example.lumira.data.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LumiraWidgetReceiver : AppWidgetProvider() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val prefs = UserPreferences(context)
            val zodiac = try {
                prefs.userZodiac.first()
            } catch (e: Exception) {
                ""
            }
            val streak = try {
                prefs.streak.first()
            } catch (e: Exception) {
                0
            }
            val name = try {
                prefs.userName.first()
            } catch (e: Exception) {
                ""
            }

            val today = try {
                java.time.LocalDate.now()
                    .format(java.time.format.DateTimeFormatter.ISO_DATE)
            } catch (e: Exception) {
                ""
            }

            val teaser = try {
                if (zodiac.isNotEmpty() && today.isNotEmpty()) {
                    FirestoreRepository()
                        .getDailyGuidance(zodiac, today)?.teaser
                        ?: "Your guidance for today is ready."
                } else {
                    "Your guidance for today is ready."
                }
            } catch (e: Exception) {
                "Your guidance for today is ready."
            }

            val displayName =
                if (name.isNotEmpty()) name else zodiac.replaceFirstChar { it.uppercase() }

            val views = RemoteViews(context.packageName, R.layout.lumira_widget_layout)
            views.setTextViewText(R.id.widget_name, displayName)
            views.setTextViewText(R.id.widget_streak, "🔥 $streak")
            views.setTextViewText(R.id.widget_teaser, teaser)

            val intent =
                android.content.Intent(context, com.example.lumira.MainActivity::class.java)
            val pendingIntent = android.app.PendingIntent.getActivity(
                context, 0, intent,
                android.app.PendingIntent.FLAG_UPDATE_CURRENT or
                        android.app.PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}