package com.madpickle.usdrate.core.di

import android.content.Context
import androidx.room.Room
import com.madpickle.usdrate.core.utils.BASE_URL
import com.madpickle.usdrate.core.utils.DATA_BASE_NAME
import com.madpickle.usdrate.database.AppDatabase
import com.madpickle.usdrate.server.XmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
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
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor;
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return  Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())                                  //Преобразователь строк и обоих примитивов и их коробочных типов в текстовые/простые тела
            .addConverterFactory(XmlConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }
}