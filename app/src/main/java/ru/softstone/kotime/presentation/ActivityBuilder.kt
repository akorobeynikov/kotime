package ru.softstone.kotime.presentation

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.softstone.kotime.presentation.main.ActivityModule
import ru.softstone.kotime.presentation.main.MainActivity


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}