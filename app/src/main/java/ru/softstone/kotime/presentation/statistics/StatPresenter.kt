package ru.softstone.kotime.presentation.statistics

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.statistics.StatInteractor
import java.util.*
import javax.inject.Inject

@InjectViewState
class StatPresenter @Inject constructor(
    private val statInteractor: StatInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val logger: Logger
) : BasePresenter<StatView>() {

    //todo вынести в настройки
    private val calendar = Calendar.getInstance().apply { add(Calendar.HOUR_OF_DAY, -4) }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showDate(calendar.time)
        showStats(calendar.time)
    }

    fun onPlusDateClick() {
        changeDate(1)
    }

    fun onMinusDateClick() {
        changeDate(-1)
    }

    private fun changeDate(amount: Int) {
        calendar.add(Calendar.DATE, amount)
        viewState.showDate(calendar.time)
        showStats(calendar.time)
    }

    private fun showStats(date: Date) {
        addDisposable(
            statInteractor.getStatistics(date)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.showStats(it)
                    logger.debug("getStatistics")
                }, {
                    logger.error("Can't get statistics", it)
                })
        )
    }

    fun onCalendarClick() {
        viewState.showDateDialog(calendar.time)
    }

    fun onDateSelected(date: Date?) {
        calendar.time = date
        viewState.showDate(calendar.time)
        showStats(calendar.time)
    }
}