package ru.softstone.kotime.presentation.main

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.presentation.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ActivityPresenter @Inject constructor(private val router: Router) : BasePresenter<ActivityView>() {
    fun onFirstCreate() {
//        router.newRootScreen(TIMER_SCREEN)
        router.newRootScreen(ERROR_SCREEN)
    }

    fun onShowTimerClick() {
        router.newRootScreen(TIMER_SCREEN)
    }

    fun onShowRecordsClick() {
        router.newRootScreen(ACTIONS_SCREEN)
    }

    fun onShowCategoriesClick() {
        router.newRootScreen(CATEGORIES_SCREEN)
    }

    fun onShowStatsClick() {
        router.newRootScreen(STAT_SCREEN)
    }
}