package com.example.zbigniew.websource

import android.app.Application
import com.example.zbigniew.websource.dagger.BaseComponent
import com.example.zbigniew.websource.dagger.BaseSystemModule
import com.example.zbigniew.websource.dagger.DaggerBaseComponent
import com.example.zbigniew.websource.dagger.DataModule

class WebSourceApp: Application(){

    companion object {
        lateinit var baseComponent: BaseComponent
    }

    override fun onCreate() {
        super.onCreate()
        baseComponent = DaggerBaseComponent.builder()
                .baseSystemModule(BaseSystemModule(this))
                .dataModule(DataModule())
                .build()
    }

}