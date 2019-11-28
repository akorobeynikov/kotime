package ru.softstone.kotime.presentation.actions.list

import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.Disposable
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.domain.action.state.AddActionState
import ru.softstone.kotime.domain.action.state.EditActionState
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.softstone.kotime.presentation.EDIT_ACTION_SCREEN
import ru.softstone.kotime.presentation.ERROR_SCREEN
import ru.softstone.kotime.presentation.customview.chart.ChartItem
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

@InjectViewState
class ActionsPresenter @Inject constructor(
    private val actionInteractor: ActionInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val errorInteractor: ErrorInteractor,
    private val router: Router,
    private val logger: Logger
) : BasePresenter<ActionsView>() {

    //todo вынести в настройки
    private val calendar = Calendar.getInstance().apply { add(Calendar.HOUR_OF_DAY, -4) }

    private var actionDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showDate(calendar.time)
        showActions(calendar.time)
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
        showActions(calendar.time)
    }

    private fun showActions(date: Date) {
        actionDisposable?.dispose()
        actionDisposable = actionInteractor.observeActions(date)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.mainScheduler())
            .subscribe({
                viewState.showActions(it)
                viewState.showChart(mapActionsToChatItems(it))
            }, {
                val wtf = "Can't observe actions"
                logger.error(wtf, it)
                errorInteractor.setLastError(wtf, it)
                router.navigateTo(ERROR_SCREEN)
            })
        addDisposable(actionDisposable!!)
    }

    private fun mapActionsToChatItems(action: List<ActionAndCategory>): List<ChartItem> {
        val startCalendar = calendar.clone() as Calendar
        startCalendar.set(Calendar.HOUR_OF_DAY, 4)
        startCalendar.set(Calendar.MINUTE, 0)
        val startChartTime = startCalendar.time.time
        return action.map {
            val startTime = (it.startTime - startChartTime) / 60000
            val endTime = (it.endTime - startChartTime) / 60000
            ChartItem(startTime.toInt(), endTime.toInt(), it.color)
        }
    }

    fun onDeleteAction(actionId: Int) {
        logger.debug("onDeleteAction $actionId")
        addDisposable(
            actionInteractor.deleteAction(actionId)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    logger.debug("Action deleted")
                }, {
                    val wtf = "Can't delete action"
                    logger.error(wtf, it)
                    errorInteractor.setLastError(wtf, it)
                    router.navigateTo(ERROR_SCREEN)
                })
        )
    }

    fun onEditAction(actionId: Int) {
        actionInteractor.setActionState(EditActionState(actionId))
        router.navigateTo(EDIT_ACTION_SCREEN)
    }

    fun onAddAction() {
        logger.debug("onAddAction")
        actionInteractor.setActionState(AddActionState())
        router.navigateTo(EDIT_ACTION_SCREEN)
    }

    fun onCalendarClick() {
        viewState.showDateDialog(calendar.time)
    }

    fun onDateSelected(date: Date?) {
        calendar.time = date
        viewState.showDate(calendar.time)
        showActions(calendar.time)
    }
}