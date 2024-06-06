package com.losrobotines.nuralign.feature_routine.domain.gemini

import com.google.ai.client.generativeai.GenerativeModel

private const val GEMINI_KEY = "AIzaSyBJT-pFK_lSZpP3CAs2CGAqKzoiGJC2Uls"

class GeminiContentGenerator  {

    private fun createGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = GEMINI_KEY
        )
    }

    suspend fun generateContent(prompt: String): String? {
        val generativeModel = createGenerativeModel()
        val response = generativeModel.generateContent(prompt)
        return response.text
    }
}