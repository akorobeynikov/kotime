package ru.softstone.kotime.presentation

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import ru.softstone.kotime.architecture.data.StateStorageImpl
import ru.softstone.kotime.architecture.presentation.SaveStateProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module(
    includes = [
        AndroidInjectionModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class,
        PresentationModule.Declarations::class
    ]
)
class PresentationModule(private val cicerone: Cicerone<Router>) {

    @Module
    internal interface Declarations {
        @Binds
        @Singleton
        fun bindSaveStateProvider(impl: StateStorageImpl): SaveStateProvider
    }

    @Provides
    @Singleton
    fun provideCicerone() = cicerone

    @Provides
    fun provideRouter(): Router = cicerone.router

    @Provides
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder
}