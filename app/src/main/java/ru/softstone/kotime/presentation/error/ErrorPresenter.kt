package ru.softstone.kotime.presentation.error

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ErrorPresenter @Inject constructor(private val router: Router) : BasePresenter<ErrorView>() {
    fun onNextButtonClick() {
        router.exit()
    }

    fun sendReport() {
    }
}