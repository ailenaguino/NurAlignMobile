package com.losrobotines.nuralign.feature_login.domain.usecases
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_login.domain.providers.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private const val DOMAIN_USER: Short = 123

private const val CURRENT_USER_UID = "a99"

class SavePatientIdOnFirestoreUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var useCase: SavePatientIdOnFirestoreUseCase

    @Before
    fun setUp(){
        authRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)
        useCase = SavePatientIdOnFirestoreUseCase(authRepository, userRepository)
    }

    @Test
    fun `it should save current user id`() = runBlocking{
        coEvery {authRepository.getCurrentUserId()} returns CURRENT_USER_UID

        useCase(DOMAIN_USER)

        coVerify{userRepository.save(CURRENT_USER_UID, DOMAIN_USER.toLong())}
    }

}