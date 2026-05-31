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
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"

    suspend fun generateReading(
        name: String,
        zodiacSign: String,
        lifePhase: String,
        readingStyle: String,
        areaOfLife: String,
        mood: String,
        question: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val prompt = buildPrompt(
                name, zodiacSign, lifePhase, readingStyle, areaOfLife, mood, question
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
                    put("temperature", 0.8)
                    put("maxOutputTokens", 700)
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
        lifePhase: String,
        readingStyle: String,
        areaOfLife: String,
        mood: String,
        question: String
    ): String {
        return """
Someone is asking for a $readingStyle reading about $areaOfLife. They are feeling $mood today and are currently $lifePhase.
Their question: "$question"
Write a helpful, specific answer to this question. Follow these rules strictly:
- Do NOT mention their name, star sign, or any astrology terms
- Do NOT start with any greeting or address them by name
- Answer the question directly in the first sentence
- Give 3 practical specific suggestions for "$areaOfLife"
- Each suggestion should be one short paragraph
- Total length: 170-200 words
- Tone: honest, warm, like advice from a knowledgeable friend
- Last line only: Affirmation: [one short sentence in first person]
    """.trimIndent()
    }
}