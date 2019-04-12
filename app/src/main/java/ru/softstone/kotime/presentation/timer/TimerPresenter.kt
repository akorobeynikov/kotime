package ru.softstone.kotime.presentation.timer

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.ERROR_SCREEN
import ru.softstone.kotime.presentation.SUGGESTION_SCREEN
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class TimerPresenter @Inject constructor(
    private val router: Router,
    private val schedulerProvider: SchedulerProvider,
    private val timeInteractor: TimeInteractor,
    private val errorInteractor: ErrorInteractor,
    private val logger: Logger
) : BasePresenter<TimerView>() {
    private var startTime = 0L

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        startTime = System.currentTimeMillis()
        updateTime(0)
        addDisposable(
            timeInteractor.isStopped()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    {
                        viewState.setIsRunning(!it)
                    },
                    {
                        val wtf = "Can't get the timer state"
                        logger.error(wtf, it)
                        errorInteractor.setLastError(wtf, it)
                        router.navigateTo(ERROR_SCREEN)
                    }
                )
        )
        addDisposable(
            Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .concatMapSingle { timeInteractor.getTimeFromStart() }
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    { updateTime(it) },
                    {
                        val wtf = "Can't getTimeFromStart"
                        logger.error(wtf, it)
                        errorInteractor.setLastError(wtf, it)
                        router.navigateTo(ERROR_SCREEN)
                    }
                )
        )
    }

    private fun updateTime(timeFromStart: Long) {
        val diffSeconds = TimeUnit.MILLISECONDS.toSeconds(timeFromStart)
        viewState.showTime(diffSeconds.toInt())
    }

    fun onTimerControlClick() {
        addDisposable(
            timeInteractor.isStopped()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    { isStopped ->
                        if (isStopped) {
                            startTimer()
                        } else {
                            viewState.showStopTimerDialog()
                        }
                    },
                    {
                        val wtf = "Can't get the timer state"
                        logger.error(wtf, it)
                        errorInteractor.setLastError(wtf, it)
                        router.navigateTo(ERROR_SCREEN)
                    }
                )
        )
    }

    private fun startTimer() {
        addDisposable(
            timeInteractor.resetTimer()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    {
                        updateTime(0)
                        viewState.setIsRunning(true)
                    },
                    {
                        val wtf = "Can't start the timer"
                        logger.error(wtf, it)
                        errorInteractor.setLastError(wtf, it)
                        router.navigateTo(ERROR_SCREEN)
                    }
                )
        )
    }

    private fun stopTimer() {
        addDisposable(
            timeInteractor.stopTimer()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    {
                        updateTime(0)
                        viewState.setIsRunning(false)
                    },
                    {
                        val wtf = "Can't stop the timer"
                        logger.error(wtf, it)
                        errorInteractor.setLastError(wtf, it)
                        router.navigateTo(ERROR_SCREEN)
                    }
                )
        )
    }

    fun onAddRecordClick() {
        router.navigateTo(SUGGESTION_SCREEN)
    }

    fun onStopTimer() {
        stopTimer()
    }
}