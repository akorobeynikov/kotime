package ru.softstone.kotime.presentation.actions.edit.behavior

import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.DisposeManager
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.state.EditSuggestionState
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.TIMER_SCREEN
import ru.softstone.kotime.presentation.actions.edit.ActionView
import ru.terrakok.cicerone.Router
import java.util.*

class EditSuggestionBehavior(
    private val state: EditSuggestionState,
    private val viewState: ActionView,
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val actionInteractor: ActionInteractor,
    private val timeInteractor: TimeInteractor,
    private val router: Router,
    private val logger: Logger
) : ActionBehavior {
    private val disposeManager = DisposeManager()

    private var initialStartTime: Long = 0
    private var initialEndTime: Long = 0

    private lateinit var startTime: Date
    private lateinit var endTime: Date

    private lateinit var categories: List<Category>

    override fun start() {
        showCategories()
        showTime()
    }

    private fun showCategories() {
        disposeManager.addDisposable(
            categoryInteractor.observeActiveCategories()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({ categories ->
                    this.categories = categories

                    viewState.showCategories(categories.map { category -> category.name })
                    val selectedCategoryId = state.categoryId
                    val index = categories.indexOfFirst { category -> category.id == selectedCategoryId }
                    viewState.setSelectedCategory(index)
                    viewState.setDescription(state.description)
                }, {
                    logger.error("Can't observe active categories", it)
                })
        )
    }

    private fun showTime() {
        disposeManager.addDisposable(
            timeInteractor.getStartTime()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({ startTime ->
                    initialStartTime = startTime
                    initialEndTime = timeInteractor.getCurrentTime()
                    setStartTime(Date(initialStartTime))
                    setEndTime(Date(initialEndTime))
                }, {
                    logger.error("Can't get start time", it)
                })
        )
    }

    private fun setStartTime(date: Date) {
        startTime = date
        viewState.showStartTime(date)
    }

    private fun setEndTime(date: Date) {
        endTime = date
        viewState.showEndTime(date)
    }


    private fun limitInInitialRange(date: Date): Date {
        if (initialStartTime != 0L && initialEndTime != 0L) {
            return when {
                date.time < initialStartTime -> Date(initialStartTime)
                date.time > initialEndTime -> Date(initialEndTime)
                else -> date
            }
        } else {
            throw IllegalStateException("Set initialStartTime and initialEndTime before")
        }
    }

    override fun startTimeChanged(date: Date) {
        val limitedTime = limitInInitialRange(date)
        setStartTime(limitedTime)
    }

    override fun endTimeChanged(date: Date) {
        val limitedTime = limitInInitialRange(date)
        setEndTime(limitedTime)
    }

    override fun onAddActionClick(description: String, categoryIndex: Int) {
        val categoryId = categories[categoryIndex].id
        disposeManager.addDisposable(
            actionInteractor.addAction(categoryId, startTime.time, endTime.time, description)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    logger.debug("Action added")
                    router.newRootScreen(TIMER_SCREEN)
                }, {
                    logger.error("Can't add action", it)
                })
        )
    }

    override fun onDestroy() {
        disposeManager.onDestroy()
    }
}