package com.madpickle.usdrate.core.di

import com.madpickle.usdrate.server.CbrUseCase
import com.madpickle.usdrate.server.ICbrService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by David Madilyan on 01.06.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideCbrUseCase(cbrService: ICbrService): CbrUseCase = CbrUseCase(cbrService)

}