package com.example.lumira.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class DailyGuidance(
    val guidance: String = "",
    val teaser: String = ""
)

class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getDailyGuidance(zodiac: String, date: String): DailyGuidance? {
        return try {
            val snapshot = db
                .collection("daily_guidance")
                .document(zodiac.lowercase())
                .collection(date)
                .document("content")
                .get()
                .await()

            if (snapshot.exists()) {
                DailyGuidance(
                    guidance = snapshot.getString("guidance") ?: "",
                    teaser = snapshot.getString("teaser") ?: ""
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}