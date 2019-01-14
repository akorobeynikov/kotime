package ru.softstone.kotime.presentation.timer

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.presentation.LOG_SCREEN
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class TimerPresenter @Inject constructor(private val router: Router) : BasePresenter<TimerView>() {
    private var startTime = 0L

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        startTime = System.currentTimeMillis()
        updateTime()
        addDisposable(
            Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribe { updateTime() }
        )
    }

    private fun updateTime() {
        val diffSeconds = (System.currentTimeMillis() - startTime) / 1000
        viewState.showTime(diffSeconds.toInt())
    }

    fun onShowLogsClick() {
        router.navigateTo(LOG_SCREEN)
    }

    fun onRecordClick() {
    }
}