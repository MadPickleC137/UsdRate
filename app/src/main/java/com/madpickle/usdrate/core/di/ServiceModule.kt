package com.madpickle.usdrate.core.di

import com.madpickle.usdrate.server.ICbrService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by David Madilyan on 01.06.2022.
 *
 * Модуль внедрения зависимостей по работе с сетью
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideCbrService(retrofit : Retrofit) : ICbrService = retrofit.create(ICbrService::class.java)
}