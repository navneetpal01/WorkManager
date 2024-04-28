package com.example.workmanager

import android.app.Application
import android.util.Log
import kotlinx.coroutines.delay


/**
 * @author Navneet Pal
 */

class DataSyncRepository(
    application : Application
){
    suspend fun syncData(){
        delay(3000)
        throw Exception()
        println("Data is synced")
    }
}