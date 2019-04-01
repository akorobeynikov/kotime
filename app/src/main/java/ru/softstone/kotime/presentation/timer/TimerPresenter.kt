package ru.softstone.kotime.presentation.timer

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.ACTIONS_SCREEN
import ru.softstone.kotime.presentation.CATEGORIES_SCREEN
import ru.softstone.kotime.presentation.STAT_SCREEN
import ru.softstone.kotime.presentation.SUGGESTION_SCREEN
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class TimerPresenter @Inject constructor(
    private val router: Router,
    private val schedulerProvider: SchedulerProvider,
    private val timeInteractor: TimeInteractor,
    private val logger: Logger
) : BasePresenter<TimerView>() {
    private var startTime = 0L

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        startTime = System.currentTimeMillis()
        updateTime(0)
        addDisposable(
            Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .concatMapSingle { timeInteractor.getTimeFromStart() }
                .subscribe { updateTime(it) }
        )
    }

    private fun updateTime(timeFromStart: Long) {
        val diffSeconds = TimeUnit.MILLISECONDS.toSeconds(timeFromStart)
        viewState.showTime(diffSeconds.toInt())
    }

    fun onShowLogsClick() {
        router.navigateTo(ACTIONS_SCREEN)
    }

    fun onShowCategoriesClick() {
        router.navigateTo(CATEGORIES_SCREEN)
    }

    fun onTimerClick() {
        addDisposable(
            timeInteractor.isStopped()
                .flatMapCompletable { timerIsStopped ->
                    if (timerIsStopped) {
                        timeInteractor.resetTimer()
                    } else {
                        timeInteractor.stopTimer()
                    }
                }
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    {
                        updateTime(0)
                    },
                    { logger.error("Can't get the timer state", it) }
                )
        )
    }

    fun onAddRecordClick() {
        router.navigateTo(SUGGESTION_SCREEN)
    }

    fun onShowStatsClick() {
        router.navigateTo(STAT_SCREEN)
    }
}