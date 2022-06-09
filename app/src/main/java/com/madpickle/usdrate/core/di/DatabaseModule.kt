package com.madpickle.usdrate.core.di

import com.madpickle.usdrate.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by David Madilyan on 31.05.2022.
 *
 * Модуль по работе с кэшом
 */

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideCourseDao(appDatabase: AppDatabase): CourseRangeDao {
        return appDatabase.courseRangeDao()
    }

    @Provides
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }

    @Provides
    fun provideCourseDayDao(appDatabase: AppDatabase): CourseDayDao {
        return appDatabase.courseDayDao()
    }

    @Provides
    fun provideNotificationCourseDao(appDatabase: AppDatabase): NotificationCourseDao {
        return appDatabase.notificationCourseDao()
    }

}