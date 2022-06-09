package com.madpickle.usdrate.database.usecase

import com.madpickle.usdrate.data.NotificationData
import com.madpickle.usdrate.database.NotificationCourseDao
import com.madpickle.usdrate.database.NotifyCourseEntity
import javax.inject.Inject

/**
 * Created by David Madilyan on 09.06.2022.
 */
class NotificationUseCase @Inject constructor(private val notificationDao: NotificationCourseDao) {
    suspend fun setNewNotification(notificationData: NotificationData){
        notificationDao.updateNotificationData(
            NotifyCourseEntity.fromNotificationData(notificationData)
        )
    }

    suspend fun getNotificationData(): NotificationData {
        val entity = notificationDao.getNotificationData()
        return entity.toNotificationData()
    }

    suspend fun deleteNotification(){
        notificationDao.deleteAll()
    }
}