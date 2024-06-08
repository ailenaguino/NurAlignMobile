package com.losrobotines.nuralign.gemini

import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.components.BuildConfig


class GeminiContentGenerator  {

    private fun createGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey ="AIzaSyBJT-pFK_lSZpP3CAs2CGAqKzoiGJC2Uls"
        )
    }

    suspend fun generateContent(prompt: String): String? {
        val generativeModel = createGenerativeModel()
        val response = generativeModel.generateContent(prompt)
        return response.text
    }
}