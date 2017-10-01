package com.example.zbigniew.websource.dagger

import android.app.AlarmManager
import android.content.Context
import android.content.res.Resources
import android.media.AudioManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class BaseSystemModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideResources(): Resources {
        return context.resources
    }
}
