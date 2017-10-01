package com.example.zbigniew.websource.dagger

import android.content.Context
import android.content.res.Resources
import com.example.zbigniew.websource.helpers.ErrorHandler
import com.example.zbigniew.websource.BuildConfig
import com.example.zbigniew.websource.repository.local.LocalDataProvider
import com.example.zbigniew.websource.repository.local.LocalDataSource
import dagger.Module
import dagger.Provides
import me.jessyan.progressmanager.ProgressManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    @Singleton
    fun provideProgressManager(): ProgressManager = ProgressManager.getInstance()

    @Provides
    @Singleton
    fun provideHttpClient(progressManager: ProgressManager): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(5, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        return progressManager.with(builder).build()
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

