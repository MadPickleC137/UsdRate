package com.madpickle.usdrate.database

import androidx.room.*
import com.madpickle.usdrate.data.NotificationData

/**
 * Created by David Madilyan on 09.06.2022.
 *
 * Отвечает за сохранение данных уведомления, необходима для воркера
 *
 *
 */
@Dao
interface NotificationCourseDao {
    @Query("SELECT * FROM notifications_data")
    suspend fun getNotificationData(): NotifyCourseEntity

    @Transaction
    suspend fun updateNotificationData(entity: NotifyCourseEntity){
        deleteAll()
        insertNotificationData(entity)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotificationData(entity: NotifyCourseEntity)

    @Query("DELETE FROM notifications_data")
    suspend fun deleteAll()
}

@Entity(tableName = "notifications_data")
data class NotifyCourseEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                             val minValue: Double? = null,
                             val codeValute: String? = null,
                             val nameValute: String? = null,
                             val maxValue: Double? = null){

    fun toNotificationData() = NotificationData(codeValute, nameValute, minValue, maxValue)

    companion object{
        fun fromNotificationData(data: NotificationData): NotifyCourseEntity{
            return NotifyCourseEntity(
                minValue = data.minValue,
                codeValute = data.codeValute,
                nameValute = data.nameValute,
                maxValue = data.maxValue,
            )
        }
    }
}