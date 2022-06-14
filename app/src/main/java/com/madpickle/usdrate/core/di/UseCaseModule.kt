package com.madpickle.usdrate.core.di

import com.madpickle.usdrate.database.CourseDayDao
import com.madpickle.usdrate.database.CourseRangeDao
import com.madpickle.usdrate.database.usecase.CoursesRangeDataSource
import com.madpickle.usdrate.database.usecase.CurrenciesDataSource
import com.madpickle.usdrate.database.CurrencyDao
import com.madpickle.usdrate.database.NotificationCourseDao
import com.madpickle.usdrate.database.usecase.CourseDayDataSource
import com.madpickle.usdrate.database.usecase.NotificationDataSource
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
object UseCaseModule {
    @Provides
    fun provideCbrUseCase(cbrService: ICbrService):
            CbrDataSource = CbrDataSource(cbrService)

    @Provides
    fun provideCurrenciesUseCase(currencyDao: CurrencyDao):
            CurrenciesDataSource = CurrenciesDataSource(currencyDao)

    @Provides
    fun provideCoursesRangeUseCase(courseRangeDao: CourseRangeDao):
            CoursesRangeDataSource = CoursesRangeDataSource(courseRangeDao)

    @Provides
    fun provideCourseDayUseCase(courseDayDao: CourseDayDao):
            CourseDayDataSource = CourseDayDataSource(courseDayDao)

    @Provides
    fun provideNotificationUseCase(notificationDao: NotificationCourseDao):
            NotificationDataSource = NotificationDataSource(notificationDao)
}