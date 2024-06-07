package com.losrobotines.nuralign.gemini

import com.google.ai.client.generativeai.GenerativeModel
import com.losrobotines.nuralign.BuildConfig.API_KEY


class GeminiContentGenerator  {

    private fun createGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = API_KEY
        )
    }

    suspend fun generateContent(prompt: String): String? {
        val generativeModel = createGenerativeModel()
        val response = generativeModel.generateContent(prompt)
        return response.text
    }
}