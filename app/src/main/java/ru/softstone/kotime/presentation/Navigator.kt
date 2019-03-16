package ru.softstone.kotime.presentation

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.softstone.kotime.architecture.presentation.BaseNavigator
import ru.softstone.kotime.presentation.category.CategoryFragment
import ru.softstone.kotime.presentation.log.LogFragment
import ru.softstone.kotime.presentation.splash.SplashFragment
import ru.softstone.kotime.presentation.suggestion.SuggestionFragment
import ru.softstone.kotime.presentation.timer.TimerFragment

class Navigator(activity: Activity, containerId: Int, fragmentManager: FragmentManager) :
    BaseNavigator(activity, containerId, fragmentManager) {

    override fun createFragment(screenKey: String?, data: Any?): Fragment = when (screenKey) {
        SPLASH_SCREEN -> SplashFragment.newInstance()
        TIMER_SCREEN -> TimerFragment.newInstance()
        LOG_SCREEN -> LogFragment.newInstance()
        CATEGORY_SCREEN -> CategoryFragment.newInstance()
        SUGGESTION_SCREEN -> SuggestionFragment.newInstance()
        else -> throw IllegalStateException("Unknown screenKey: $screenKey")
    }
}