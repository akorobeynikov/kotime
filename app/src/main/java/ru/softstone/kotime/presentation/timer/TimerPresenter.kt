package ru.softstone.kotime.presentation.timer

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.ABOUT_SCREEN
import ru.softstone.kotime.presentation.CONTRIBUTE_SCREEN
import ru.softstone.kotime.presentation.ERROR_SCREEN
import ru.softstone.kotime.presentation.SUGGESTION_SCREEN
import ru.softstone.kotime.presentation.customview.chart.ChartItem
import ru.terrakok.cicerone.Router
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class TimerPresenter @Inject constructor(
    private val router: Router,
    private val schedulerProvider: SchedulerProvider,
    private val timeInteractor: TimeInteractor,
    private val errorInteractor: ErrorInteractor,
    private val actionInteractor: ActionInteractor,
    private val logger: Logger
) : BasePresenter<TimerView>() {
    private var startTime = 0L

    //todo вынести в настройки
    private val calendar = Calendar.getInstance().apply { add(Calendar.HOUR_OF_DAY, -4) }

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
        observeActions()
    }

    private fun updateTime(timeFromStart: Long) {
        val diffSeconds = TimeUnit.MILLISECONDS.toSeconds(timeFromStart)
        viewState.showTime(diffSeconds.toInt())
        val calendar = Calendar.getInstance()
        val currentTime  = calendar.time.time
        calendar.apply {
            add(Calendar.HOUR_OF_DAY, -4)  //todo вынести в настройки
            set(Calendar.HOUR_OF_DAY, 4)//todo вынести в настройки
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val startTime = calendar.time.time
        viewState.showTimeMarks((currentTime - startTime).toInt() / 60000)
    }

    private fun observeActions() {
        addDisposable(
            actionInteractor.observeActions(calendar.time)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.showChart(mapActionsToChatItems(it))
                }, {
                    val wtf = "Can't observe actions"
                    logger.error(wtf, it)
                    errorInteractor.setLastError(wtf, it)
                    router.navigateTo(ERROR_SCREEN)
                })
        )
        // todo вынести в настройки
        calendar.set(Calendar.HOUR_OF_DAY, 4)
        calendar.set(Calendar.MINUTE, 0)
    }

    private fun mapActionsToChatItems(action: List<ActionAndCategory>) : List<ChartItem> {
        val startChartTime = calendar.time.time
        return action.map {
            val startTime = (it.startTime - startChartTime) / 60000
            val endTime = (it.endTime - startChartTime) / 60000
            ChartItem(startTime.toInt(), endTime.toInt(), it.color)
        }
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

    fun onContributeClick() {
        router.navigateTo(CONTRIBUTE_SCREEN)
    }

    fun onAboutClick() {
        router.navigateTo(ABOUT_SCREEN)
    }
}