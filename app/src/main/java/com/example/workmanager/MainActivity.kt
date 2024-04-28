package com.example.workmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWorkManager()
        setContent {

        }
    }
    private fun initWorkManager(){

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(networkType = NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(requiresBatteryNotLow = true)
            .setRequiresStorageNotLow(requiresStorageNotLow = true)
            .build()


        val workRequest = OneTimeWorkRequestBuilder<DataSyncWorker>()
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.LINEAR,
                duration = Duration.ofSeconds(15)
            )
            .setInitialDelay(Duration.ofSeconds(10))
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueue(workRequest)
    }
    private fun initPeriodicWorkManager(){

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(networkType = NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(requiresBatteryNotLow = true)
            .setRequiresStorageNotLow(requiresStorageNotLow = true)
            .build()


        val workRequest = PeriodicWorkRequestBuilder<DataSyncWorker>(1,TimeUnit.HOURS)
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.LINEAR,
                duration = Duration.ofSeconds(15)
            )
            .setInitialDelay(Duration.ofSeconds(10))
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "DATA_SYNC_WORKER",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}