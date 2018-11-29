package ru.softstone.kotime.presentation

import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module(
    includes = [
        AndroidInjectionModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class
    ]
)
class PresentationModule(private val cicerone: Cicerone<Router>) {
    @Provides
    @Singleton
    fun provideCicerone() = cicerone

    @Provides
    fun provideRouter(): Router = cicerone.router

    @Provides
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder
}