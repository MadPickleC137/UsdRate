package com.madpickle.usdrate.database.datasource

import com.madpickle.usdrate.data.NotificationData
import com.madpickle.usdrate.database.NotificationCourseDao
import com.madpickle.usdrate.database.NotifyCourseEntity
import javax.inject.Inject

/**
 * Created by David Madilyan on 09.06.2022.
 */
class NotificationDataSource @Inject constructor(private val notificationDao: NotificationCourseDao) {
    suspend fun setNewNotification(notificationData: NotificationData){
        notificationDao.updateNotificationData(
            NotifyCourseEntity.fromNotificationData(notificationData)
        )
    }
}