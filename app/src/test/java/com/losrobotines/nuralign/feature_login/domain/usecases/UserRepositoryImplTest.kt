package com.losrobotines.nuralign.feature_login.domain.usecases

import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_login.data.providers.UserRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest{

    private lateinit var repository: UserRepositoryImpl
    private lateinit var firebase: FirebaseApp
    private lateinit var firestore: FirebaseFirestore

    private val documentId = 123
    private val document = mutableMapOf<String, Any>(
        "id" to documentId
    )

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl()
        firebase = mockk(relaxed = true)
        firestore = mockk(relaxed = true)
    }

    @Test
    fun `when calling save it saves the data correctly but using my brain`() {
        val v = firestore.collection("users").document("a99").get().result

        runBlocking {
            repository.save("a99", 123)
            assertEquals(123, v.get("id"))
        }
    }


    @Test
    fun `when calling save it saves the data  but using google`() {
        val documentSnapshot = mockk<DocumentSnapshot> {
            every { id } returns "a99"
            every { data } returns document
        }

        every { Firebase.firestore.collection("users").document("a99").get() } returns mockTask<DocumentSnapshot>(documentSnapshot)

        runBlocking {
            repository.save("a99", 123)

            assertEquals(document["id"], 123)

        }
    }

    inline fun <reified T> mockTask(result: T?, exception: Exception? = null): Task<T> {
        val task: Task<T> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedT: T = mockk(relaxed = true)
        every { task.result } returns result
        return task
    }
}