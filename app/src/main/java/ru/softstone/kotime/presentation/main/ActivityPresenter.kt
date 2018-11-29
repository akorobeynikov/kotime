package ru.softstone.kotime.presentation.main

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.presentation.SPLASH_SCREEN
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ActivityPresenter @Inject constructor(private val router: Router) : BasePresenter<ActivityView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(SPLASH_SCREEN)
    }
}