package com.losrobotines.nuralign.feature_routine.domain.notification.gemini

import com.google.ai.client.generativeai.GenerativeModel

class GeminiContentGenerator  {

    private fun createGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey ="AIzaSyBbsws2zdhgtwuh16ltff6p19YwELV85E0"
        )
    }

    suspend fun generateContent(prompt: String): String? {
        val generativeModel = createGenerativeModel()
        val response = generativeModel.generateContent(prompt)
        return response.text
    }
}