package com.losrobotines.nuralign.feature_login.domain.usecases
import android.os.Looper
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.util.Executors
import com.google.firebase.firestore.util.Util.voidErrorTransformer
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class SavePatientIdOnFirestoreUseCaseTest {
    /*
    private lateinit var path: String
    private lateinit var uid: String
    private var patientId = 0.toShort()
    private lateinit var user: MutableMap<String, Any>
    private lateinit var useCase: SavePatientIdOnFirestoreUseCase

    @Before
    fun setUp() {
        path = "users"
        uid = "fhjesaufheaFERAghfueaG"
        patientId = 25072
        user = mutableMapOf(
            "id" to patientId
        )
        useCase = SavePatientIdOnFirestoreUseCase()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test creation document`() {
        //Given
        val taskCompletionSource = TaskCompletionSource<Void>()

        mockkStatic(Looper::class)
        every { Looper.getMainLooper() } returns mockk()

        mockkStatic(android.os.Process::class)
        every { android.os.Process.myPid() } returns mockk()

        val mockdb: FirebaseFirestore = mockk {
            every {
                collection(path)
                    .document(uid)
                    .set(user)
            } returns taskCompletionSource.task.continueWith(
                Executors.DIRECT_EXECUTOR,
                voidErrorTransformer()
            )
        }

        //When
        useCase(patientId)

        //Then
        verify(exactly = 1) {
            mockdb.collection(path).document(uid).set(user)
        }
        confirmVerified(mockdb)
    }

    inline fun <reified T> mockTask(result: T?, exception: Exception? = null): Task<T> {
        val task: Task<T> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedT: T = mockk(relaxed = true)
        every { task.result } returns result
        return task
    }*/
}