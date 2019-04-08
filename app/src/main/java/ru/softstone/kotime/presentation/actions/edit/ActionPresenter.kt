package ru.softstone.kotime.presentation.actions.edit

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.state.ActionState
import ru.softstone.kotime.domain.action.state.AddActionState
import ru.softstone.kotime.domain.action.state.EditActionState
import ru.softstone.kotime.domain.action.state.EditSuggestionState
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.actions.edit.behavior.ActionBehavior
import ru.softstone.kotime.presentation.actions.edit.behavior.AddActionBehavior
import ru.softstone.kotime.presentation.actions.edit.behavior.EditActionBehavior
import ru.softstone.kotime.presentation.actions.edit.behavior.EditSuggestionBehavior
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

@InjectViewState
class ActionPresenter @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val actionInteractor: ActionInteractor,
    private val timeInteractor: TimeInteractor,
    private val router: Router,
    private val logger: Logger
) : BasePresenter<ActionView>() {
    private lateinit var behavior: ActionBehavior

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        addDisposable(
            actionInteractor.getActionState()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({ state ->
                    logger.debug("getActionState $state")
                    behavior = getBehavior(state)
                    behavior.start()
                }, {
                    logger.error("Can't get start time", it)
                })
        )
    }

    private fun getBehavior(state: ActionState): ActionBehavior {
        return when (state) {
            // todo инстацировать через dagger
            is AddActionState -> AddActionBehavior(
                viewState,
                categoryInteractor,
                schedulerProvider,
                actionInteractor,
                timeInteractor,
                router,
                logger
            )
            // todo инстацировать через dagger
            is EditActionState -> EditActionBehavior(
                state,
                viewState,
                categoryInteractor,
                schedulerProvider,
                actionInteractor,
                timeInteractor,
                router,
                logger
            )
            // todo инстацировать через dagger
            is EditSuggestionState -> EditSuggestionBehavior(
                state,
                viewState,
                categoryInteractor,
                schedulerProvider,
                actionInteractor,
                timeInteractor,
                router,
                logger
            )
            else -> throw IllegalStateException("Unknown type of state")
        }
    }

    fun startTimeChanged(date: Date) {
        behavior.startTimeChanged(date)
    }


    fun endTimeChanged(date: Date) {
        behavior.endTimeChanged(date)
    }

    fun onAddActionClick(description: String) {
        behavior.onAddActionClick(description)
    }

    override fun onDestroy() {
        behavior.onDestroy()
        super.onDestroy()
    }

    fun onMinusDurationClick() {
        behavior.onMinusDurationClick()
    }

    fun onPlusDurationClick() {
        behavior.onPlusDurationClick()
    }

    fun onBackPressed() {
        router.exit()
    }

    fun onCategoryClick() {
        behavior.onCategoryClick()
    }

    fun onCategorySelected(index: Int) {
        behavior.onCategorySelected(index)
    }
}