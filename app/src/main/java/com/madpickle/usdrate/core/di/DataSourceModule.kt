package com.madpickle.usdrate.core.di

import com.madpickle.usdrate.database.CourseDayDao
import com.madpickle.usdrate.database.CourseRangeDao
import com.madpickle.usdrate.database.datasource.CoursesRangeDataSource
import com.madpickle.usdrate.database.datasource.CurrenciesDataSource
import com.madpickle.usdrate.database.CurrencyDao
import com.madpickle.usdrate.database.NotificationCourseDao
import com.madpickle.usdrate.database.datasource.CourseDayDataSource
import com.madpickle.usdrate.database.datasource.NotificationDataSource
import com.madpickle.usdrate.remote.CbrDataSource
import com.madpickle.usdrate.remote.ICbrService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by David Madilyan on 01.06.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    fun provideCbrDataSource(cbrService: ICbrService):
            CbrDataSource = CbrDataSource(cbrService)

    @Provides
    fun provideCurrenciesDataSource(currencyDao: CurrencyDao):
            CurrenciesDataSource = CurrenciesDataSource(currencyDao)

    @Provides
    fun provideCoursesRangeDataSource(courseRangeDao: CourseRangeDao):
            CoursesRangeDataSource = CoursesRangeDataSource(courseRangeDao)

    @Provides
    fun provideCourseDayDataSource(courseDayDao: CourseDayDao):
            CourseDayDataSource = CourseDayDataSource(courseDayDao)

    @Provides
    fun provideNotificationDataSource(notificationDao: NotificationCourseDao):
            NotificationDataSource = NotificationDataSource(notificationDao)
}