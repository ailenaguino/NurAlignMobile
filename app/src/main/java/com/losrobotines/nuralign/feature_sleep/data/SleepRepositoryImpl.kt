package com.losrobotines.nuralign.feature_sleep.data

import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo

class SleepRepositoryImpl : SleepRepository {

    //private lateinit var database: Database
    override suspend fun saveSleepData(sleepInfo: SleepInfo) {
        /*
        CoroutineScope(Dispatchers.IO).launch {
            database = Database.connect(
                "jdbc:mariadb://77.37.69.38:3306/nuralign",
                user = "root",
                password = "/rt(Chw[-A(K@y8("
            )
        }*/


    }
}