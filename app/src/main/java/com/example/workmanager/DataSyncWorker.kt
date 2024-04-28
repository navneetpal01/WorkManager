package com.example.workmanager

import android.annotation.SuppressLint
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


/**
 * TODO- Two types of worker that you can Inherit worker(),coroutineWorker()
 */
@HiltWorker
class DataSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val dataSyncRepository: DataSyncRepository
) : CoroutineWorker(
    context,
    workerParameters
) {
    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        return try {
            dataSyncRepository.syncData()
            return Result.success()
        } catch (e: Exception) {
            Result.Retry()
        }
    }

}