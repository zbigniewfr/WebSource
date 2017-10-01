package com.example.zbigniew.websource.dagger

import android.content.Context
import android.content.res.Resources
import com.example.zbigniew.websource.ErrorHandler
import com.example.zbigniew.websource.BuildConfig
import com.futuremind.omili.repository.local.LocalDataProvider
import com.example.zbigniew.websource.repository.local.LocalDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(5, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(cxt: Context): LocalDataSource {
        return LocalDataProvider().provide(cxt)
    }

    @Provides
    @Singleton
    fun provideErrorHandler(resources: Resources): ErrorHandler {
        return ErrorHandler(resources)
    }
}

