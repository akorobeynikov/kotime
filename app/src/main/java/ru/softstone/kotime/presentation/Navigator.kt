package ru.softstone.kotime.presentation

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.softstone.kotime.architecture.presentation.BaseNavigator
import ru.softstone.kotime.presentation.actions.edit.ActionFragment
import ru.softstone.kotime.presentation.actions.list.ActionsFragment
import ru.softstone.kotime.presentation.category.edit.CategoryFragment
import ru.softstone.kotime.presentation.category.list.CategoriesFragment
import ru.softstone.kotime.presentation.error.ErrorFragment
import ru.softstone.kotime.presentation.splash.SplashFragment
import ru.softstone.kotime.presentation.statistics.StatFragment
import ru.softstone.kotime.presentation.suggestion.SuggestionFragment
import ru.softstone.kotime.presentation.timer.TimerFragment

class Navigator(activity: Activity, containerId: Int, fragmentManager: FragmentManager) :
    BaseNavigator(activity, containerId, fragmentManager) {

    override fun createFragment(screenKey: String?, data: Any?): Fragment = when (screenKey) {
        SPLASH_SCREEN -> SplashFragment.newInstance()
        TIMER_SCREEN -> TimerFragment.newInstance()
        ACTIONS_SCREEN -> ActionsFragment.newInstance()
        EDIT_ACTION_SCREEN -> ActionFragment.newInstance()
        CATEGORIES_SCREEN -> CategoriesFragment.newInstance()
        CATEGORY_SCREEN -> CategoryFragment.newInstance()
        SUGGESTION_SCREEN -> SuggestionFragment.newInstance()
        STAT_SCREEN -> StatFragment.newInstance()
        ERROR_SCREEN -> ErrorFragment.newInstance()
        else -> throw IllegalStateException("Unknown screenKey: $screenKey")
    }
}