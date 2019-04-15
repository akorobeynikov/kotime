package ru.softstone.kotime.presentation.contribute

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ContributePresenter @Inject constructor(
    private val router: Router
) : BasePresenter<ContributeView>() {
    fun onBackPressed() {
        router.exit()
    }
}