package com.madpickle.usdrate.worker

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.madpickle.usdrate.core.extensions.getStringSlashDate
import com.madpickle.usdrate.core.utils.BASE_URL
import com.madpickle.usdrate.core.utils.DATA_BASE_NAME
import com.madpickle.usdrate.core.view.NotificationBuilder
import com.madpickle.usdrate.data.CourseRange
import com.madpickle.usdrate.data.NotificationData
import com.madpickle.usdrate.database.AppDatabase
import com.madpickle.usdrate.remote.CbrUseCase
import com.madpickle.usdrate.remote.ICbrService
import com.madpickle.usdrate.remote.XmlConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDateTime


/**
 * Created by David Madilyan on 09.06.2022.
 */
class CourseEqualWorker(private val ctx: Context, params: WorkerParameters):
    CoroutineWorker(ctx, params) {
    private var counter = 0
    override suspend fun doWork(): Result {
        val database = Room.databaseBuilder(ctx, AppDatabase::class. java, DATA_BASE_NAME).build()
        val notifyData = withContext(Dispatchers.IO){
            database.notificationCourseDao().getNotificationData()
        }.toNotificationData()
        if(notifyData.codeValute == null
            || notifyData.maxValue == null
            || notifyData.minValue == null){
            return Result.failure()
        }
        val dateNow = LocalDateTime.now().getStringSlashDate()
        val cbrUseCase = makeRemoteDependency()
        val courseValute = withContext(Dispatchers.IO){
            cbrUseCase.getCourseRange(dateNow, dateNow, notifyData.codeValute)?.firstOrNull()
        } ?: return Result.failure()
        if (courseValute.value == null) return Result.failure()
        makeNotification(
            courseValute.value,
            notifyData.maxValue,
            notifyData,
            courseValute,
            notifyData.minValue
        )
        return Result.success()
    }

    private suspend fun makeNotification(
        value: Double,
        maxValue: Double,
        notifyData: NotificationData,
        courseValute: CourseRange,
        minValue: Double
    ) = withContext(Dispatchers.Main) {
        val notification = NotificationBuilder(ctx)
        if (value > maxValue) {
            //make notify max
            NotificationManagerCompat.from(ctx).notify(
                counter++, notification.createNotify(
                    null,
                    "${notifyData.nameValute} stonks!",
                    "Стоимость валюты: ${courseValute.value} рублей"
                )
            )
        } else if (value < minValue) {
            //make notify min
            NotificationManagerCompat.from(ctx).notify(
                counter++, notification.createNotify(
                    null,
                    "${notifyData.nameValute} not stonks!",
                    "Стоимость валюты: ${courseValute.value}"
                )
            )
        }
    }

    private fun makeRemoteDependency(): CbrUseCase {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(XmlConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .build()
        val cbrService = retrofit.create(ICbrService::class.java)
        return  CbrUseCase(cbrService)
    }
}