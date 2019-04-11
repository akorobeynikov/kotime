package ru.softstone.kotime.presentation.error

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.common.StringProvider
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.terrakok.cicerone.Router
import java.io.PrintWriter
import java.io.StringWriter
import javax.inject.Inject

@InjectViewState
class ErrorPresenter @Inject constructor(
    private val router: Router,
    private val stringProvider: StringProvider,
    private val errorInteractor: ErrorInteractor
) : BasePresenter<ErrorView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val error = errorInteractor.getLastError()
        error?.let {
            viewState.showError(it.throwable.toString())
        }
    }

    fun onNextButtonClick() {
        router.exit()
    }

    fun sendReport() {
        val error = errorInteractor.getLastError()
        val emailBody = if (error != null) {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            error.throwable.printStackTrace(pw)
            val errorStackTrace = sw.toString()
            val errorInfoText = errorStackTrace + "\n" + error.description
            stringProvider.getString(R.string.support_email_body, errorInfoText)
        } else {
            ""
        }
        viewState.sendEmail(emailBody)
    }
}