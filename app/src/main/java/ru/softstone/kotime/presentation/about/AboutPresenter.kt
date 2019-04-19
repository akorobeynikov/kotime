package ru.softstone.kotime.presentation.about

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class AboutPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<AboutView>() {
    fun onBackPressed() {
        router.exit()
    }
}