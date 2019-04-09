package ru.softstone.kotime.presentation.actions.list

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.state.AddActionState
import ru.softstone.kotime.domain.action.state.EditActionState
import ru.softstone.kotime.presentation.EDIT_ACTION_SCREEN
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

@InjectViewState
class ActionsPresenter @Inject constructor(
    private val actionInteractor: ActionInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val router: Router,
    private val logger: Logger
) : BasePresenter<ActionsView>() {

    private val calendar = Calendar.getInstance()

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
        addDisposable(
            actionInteractor.observeActions(date)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.showActions(it)
                }, {
                    logger.error("Can't observe actions", it)
                })
        )
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
                    logger.error("Can't delete action", it)
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