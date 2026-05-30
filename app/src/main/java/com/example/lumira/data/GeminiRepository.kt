package com.example.lumira.data

import com.example.lumira.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class GeminiRepository {

    private val apiKey = BuildConfig.GEMINI_API_KEY
    private val endpoint =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey"

    suspend fun generateReading(
        name: String,
        zodiacSign: String,
        chineseZodiac: String,
        lifePathNumber: Int,
        personalDayNumber: Int,
        lifePhase: String,
        birthTime: String,
        readingStyle: String,
        areaOfLife: String,
        mood: String,
        question: String,
        astrologyContext: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val prompt = buildPrompt(
                name, zodiacSign, chineseZodiac, lifePathNumber,
                personalDayNumber, lifePhase, birthTime, readingStyle,
                areaOfLife, mood, question, astrologyContext
            )

            val requestBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.9)
                    put("maxOutputTokens", 400)
                })
            }

            val url = URL(endpoint)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.connectTimeout = 15000
            connection.readTimeout = 15000

            connection.outputStream.use { it.write(requestBody.toString().toByteArray()) }

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                val response = connection.inputStream.bufferedReader().readText()
                val json = JSONObject(response)
                val text = json
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text")
                Result.success(text.trim())
            } else {
                val error = connection.errorStream?.bufferedReader()?.readText() ?: "Unknown error"
                Result.failure(Exception("API error $responseCode: $error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun buildPrompt(
        name: String,
        zodiacSign: String,
        chineseZodiac: String,
        lifePathNumber: Int,
        personalDayNumber: Int,
        lifePhase: String,
        birthTime: String,
        readingStyle: String,
        areaOfLife: String,
        mood: String,
        question: String,
        astrologyContext: String
    ): String {
        val birthTimeText = if (birthTime.isNotEmpty()) "born at $birthTime" else ""
        return """
You are a warm, insightful spiritual guide writing a personalised daily reading.

User profile:
- Name: $name
- Sun sign: $zodiacSign $birthTimeText
- Chinese zodiac: $chineseZodiac
- Life path number: $lifePathNumber
- Personal day number: $personalDayNumber
- Life phase: $lifePhase

Today's cosmic energy:
- $astrologyContext

Reading request:
- Style: $readingStyle
- Area of life: $areaOfLife
- Current mood: $mood
- Question: $question

Write a warm, personal 180-200 word reading for $name that directly addresses their question about $areaOfLife. Weave in their $zodiacSign energy and today's cosmic context naturally. End with a one sentence affirmation starting with "Affirmation:". Do not use bullet points. Write in flowing paragraphs. Do not mention the user's life path or personal day number directly — let them subtly inform the tone.
        """.trimIndent()
    }
}