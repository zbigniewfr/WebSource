package com.example.zbigniew.websource.dagger

import com.example.zbigniew.websource.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(BaseSystemModule::class, DataModule::class))
interface BaseComponent {
    fun inject(view: MainActivity)
}