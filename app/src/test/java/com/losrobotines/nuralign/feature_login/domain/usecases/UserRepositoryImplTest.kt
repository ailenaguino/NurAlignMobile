package com.losrobotines.nuralign.feature_login.domain.usecases

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_login.data.providers.UserRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserRepositoryImplTest{

    private lateinit var repository: UserRepositoryImpl

    @BeforeEach
    fun setUp(){
        repository = UserRepositoryImpl()
    }


    @Test
    fun `sarasa`() = runBlocking{
        repository.save("a99", 123)
        val v = Firebase.firestore.collection("users").document("a99")

    }
}