package com.madpickle.usdrate.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by David Madilyan on 31.05.2022.
 */
@Database(entities = [], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun courseDayDao(): CourseDayDao
}