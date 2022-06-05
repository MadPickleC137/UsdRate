package com.madpickle.usdrate.core.di

import com.madpickle.usdrate.database.CourseDayDao
import com.madpickle.usdrate.database.CourseRangeDao
import com.madpickle.usdrate.database.usecase.CoursesRangeUseCase
import com.madpickle.usdrate.database.usecase.CurrenciesUseCase
import com.madpickle.usdrate.database.CurrencyDao
import com.madpickle.usdrate.database.usecase.CourseDayUseCase
import com.madpickle.usdrate.remote.CbrUseCase
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
    fun provideCbrUseCase(cbrService: ICbrService): CbrUseCase = CbrUseCase(cbrService)

    @Provides
    fun provideCurrenciesUseCase(currencyDao: CurrencyDao): CurrenciesUseCase = CurrenciesUseCase(currencyDao)

    @Provides
    fun provideCoursesRangeUseCase(courseRangeDao: CourseRangeDao): CoursesRangeUseCase = CoursesRangeUseCase(courseRangeDao)

    @Provides
    fun provideCourseDayUseCase(courseDayDao: CourseDayDao): CourseDayUseCase = CourseDayUseCase(courseDayDao)
}