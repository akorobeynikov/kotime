package ru.softstone.kotime

import ru.softstone.kotime.architecture.BaseApplication
import ru.softstone.kotime.presentation.PresentationModule
import ru.terrakok.cicerone.Cicerone

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val appComponent = DaggerAppComponent.builder()
            .application(this)
            .presentationModule(PresentationModule(Cicerone.create()))
            .build()
        appComponent.inject(this)
    }
}