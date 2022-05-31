package com.madpickle.usdrate.core.module

import com.madpickle.usdrate.database.AppDatabase
import com.madpickle.usdrate.database.CourseDao
import com.madpickle.usdrate.database.CourseDayDao
import com.madpickle.usdrate.database.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by David Madilyan on 31.05.2022.
 */

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideCourseDao(appDatabase: AppDatabase): CourseDao {
        return appDatabase.courseDao()
    }

    @Provides
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }

    @Provides
    fun provideCourseDayDao(appDatabase: AppDatabase): CourseDayDao {
        return appDatabase.courseDayDao()
    }
}