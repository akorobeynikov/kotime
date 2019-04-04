package ru.softstone.kotime.presentation.main

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.presentation.ACTIONS_SCREEN
import ru.softstone.kotime.presentation.CATEGORIES_SCREEN
import ru.softstone.kotime.presentation.STAT_SCREEN
import ru.softstone.kotime.presentation.TIMER_SCREEN
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ActivityPresenter @Inject constructor(private val router: Router) : BasePresenter<ActivityView>() {
    fun onFirstCreate() {
        router.newRootScreen(TIMER_SCREEN)
    }

    fun onShowTimerClick() {
        router.navigateTo(TIMER_SCREEN)
    }

    fun onShowRecordsClick() {
        router.navigateTo(ACTIONS_SCREEN)
    }

    fun onShowCategoriesClick() {
        router.navigateTo(CATEGORIES_SCREEN)
    }

    fun onShowStatsClick() {
        router.navigateTo(STAT_SCREEN)
    }
}