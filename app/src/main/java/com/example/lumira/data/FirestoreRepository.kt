package com.example.lumira.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.lumira.model.ReadingSession
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
            } else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveReadingSession(session: ReadingSession) {
        try {
            val zodiac = session.zodiacSign.lowercase()
            db.collection("readings")
                .document(zodiac)
                .collection("sessions")
                .document(session.id)
                .set(
                    mapOf(
                        "id" to session.id,
                        "date" to session.date,
                        "readingStyle" to session.readingStyle,
                        "areaOfLife" to session.areaOfLife,
                        "mood" to session.mood,
                        "question" to session.question,
                        "response" to session.response,
                        "zodiacSign" to session.zodiacSign,
                        "timestamp" to session.timestamp
                    )
                )
                .await()
        } catch (e: Exception) {
        }
    }

    suspend fun getReadingHistory(zodiac: String, limit: Int): List<ReadingSession> {
        return try {
            val snapshot = db.collection("readings")
                .document(zodiac.lowercase())
                .collection("sessions")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                try {
                    ReadingSession(
                        id = doc.getString("id") ?: "",
                        date = doc.getString("date") ?: "",
                        readingStyle = doc.getString("readingStyle") ?: "",
                        areaOfLife = doc.getString("areaOfLife") ?: "",
                        mood = doc.getString("mood") ?: "",
                        question = doc.getString("question") ?: "",
                        response = doc.getString("response") ?: "",
                        zodiacSign = doc.getString("zodiacSign") ?: "",
                        timestamp = doc.getLong("timestamp") ?: 0L
                    )
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}