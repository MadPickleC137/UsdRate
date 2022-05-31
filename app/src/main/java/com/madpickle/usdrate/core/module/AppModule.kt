package com.madpickle.usdrate.core.module

import android.content.Context
import androidx.room.Room
import com.madpickle.usdrate.core.utils.BASE_URL
import com.madpickle.usdrate.core.utils.DATA_BASE_NAME
import com.madpickle.usdrate.database.AppDatabase
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by David Madilyan on 31.05.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun baseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class. java,
            DATA_BASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        return  Retrofit.Builder()
            .addConverterFactory(TikXmlConverterFactory.create())
            .baseUrl(baseUrl)
            .client(OkHttpClient())
            .build()
    }
}