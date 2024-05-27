package com.losrobotines.nuralign.feature_login.domain.providers

import com.losrobotines.nuralign.feature_login.domain.models.User

interface UserRepository {

    fun save(user: User)

    suspend fun save(currentUserId: String, domainUserId: Long)
}