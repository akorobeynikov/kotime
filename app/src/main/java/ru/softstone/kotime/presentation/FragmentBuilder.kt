package ru.softstone.kotime.presentation

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.softstone.kotime.presentation.category.CategoryFragment
import ru.softstone.kotime.presentation.category.CategoryModule
import ru.softstone.kotime.presentation.log.LogFragment
import ru.softstone.kotime.presentation.log.LogModule
import ru.softstone.kotime.presentation.splash.SplashFragment
import ru.softstone.kotime.presentation.splash.SplashModule
import ru.softstone.kotime.presentation.suggestion.SuggestionFragment
import ru.softstone.kotime.presentation.suggestion.SuggestionModule
import ru.softstone.kotime.presentation.timer.TimerFragment
import ru.softstone.kotime.presentation.timer.TimerModule

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun bindSplashFragment(): SplashFragment

    @ContributesAndroidInjector(modules = [TimerModule::class])
    internal abstract fun bindTimerFragment(): TimerFragment

    @ContributesAndroidInjector(modules = [LogModule::class])
    internal abstract fun bindLogFragment(): LogFragment

    @ContributesAndroidInjector(modules = [CategoryModule::class])
    internal abstract fun bindCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector(modules = [SuggestionModule::class])
    internal abstract fun bindSuggestionFragment(): SuggestionFragment
}