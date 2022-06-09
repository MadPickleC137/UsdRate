package com.madpickle.usdrate.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by David Madilyan on 31.05.2022.
 */
@Database(entities = [
    CourseRangeEntity::class,
    CourseDayEntity::class,
    CurrencyEntity::class,
    NotifyCourseEntity::class
                     ], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun courseRangeDao(): CourseRangeDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun courseDayDao(): CourseDayDao
    abstract fun notificationCourseDao(): NotificationCourseDao
}