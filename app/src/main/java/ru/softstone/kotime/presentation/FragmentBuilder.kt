package ru.softstone.kotime.presentation

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.softstone.kotime.presentation.splash.SplashFragment
import ru.softstone.kotime.presentation.splash.SplashModule

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun bindSplashFragment(): SplashFragment
}