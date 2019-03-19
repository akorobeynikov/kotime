package ru.softstone.kotime.presentation

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.softstone.kotime.presentation.actions.edit.EditActionFragment
import ru.softstone.kotime.presentation.actions.edit.EditActionModule
import ru.softstone.kotime.presentation.actions.list.ActionsFragment
import ru.softstone.kotime.presentation.actions.list.ActionsModule
import ru.softstone.kotime.presentation.category.CategoryFragment
import ru.softstone.kotime.presentation.category.CategoryModule
import ru.softstone.kotime.presentation.splash.SplashFragment
import ru.softstone.kotime.presentation.splash.SplashModule
import ru.softstone.kotime.presentation.statistics.StatFragment
import ru.softstone.kotime.presentation.statistics.StatModule
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

    @ContributesAndroidInjector(modules = [ActionsModule::class])
    internal abstract fun bindActionsFragment(): ActionsFragment

    @ContributesAndroidInjector(modules = [EditActionModule::class])
    internal abstract fun bindEditActionFragment(): EditActionFragment

    @ContributesAndroidInjector(modules = [CategoryModule::class])
    internal abstract fun bindCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector(modules = [SuggestionModule::class])
    internal abstract fun bindSuggestionFragment(): SuggestionFragment

    @ContributesAndroidInjector(modules = [StatModule::class])
    internal abstract fun bindStatFragment(): StatFragment
}