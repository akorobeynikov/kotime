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
import java.util.concurrent.TimeUnit

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
    private var selectedCategoryIndex = 0

    private val calendar = Calendar.getInstance()

    override fun start() {
        viewState.setDescription(state.description)
        getCategories()
        showTime()
    }

    private fun getCategories() {
        disposeManager.addDisposable(
            categoryInteractor.observeActiveCategories()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({ categories ->
                    this.categories = categories
                    val selectedCategoryId = state.categoryId
                    //todo упадет если категория будет неактивная
                    selectedCategoryIndex = categories.indexOfFirst { category -> category.id == selectedCategoryId }
                    viewState.showSelectedCategory(categories[selectedCategoryIndex].name)
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
                    updateDuration()
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
        updateDuration()
    }

    override fun endTimeChanged(date: Date) {
        val limitedTime = limitInInitialRange(date)
        setEndTime(limitedTime)
        updateDuration()
    }

    override fun onAddActionClick(description: String) {
        val categoryId = categories[selectedCategoryIndex].id
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

    override fun onMinusDurationClick() {
        changeEndTime(-5)
    }

    override fun onPlusDurationClick() {
        changeEndTime(5)
    }

    override fun onCategoryClick() {
        //todo может успасть, если пользователь кликнет до получения списка категорий
        viewState.showCategories(categories.map { it.name })
    }

    override fun onCategorySelected(index: Int) {
        selectedCategoryIndex = index
        viewState.showSelectedCategory(categories[selectedCategoryIndex].name)
    }

    private fun updateDuration() {
        val durationSeconds = getDurationSeconds().toInt()
        val correctionSeconds = getCorrectionSeconds().toInt()
        if (durationSeconds >= 0) {
            viewState.showDuration(durationSeconds, correctionSeconds)
        }
    }

    private fun getDurationSeconds(): Long {
        val milliseconds = endTime.time - startTime.time
        return TimeUnit.MILLISECONDS.toSeconds(milliseconds)
    }

    private fun getCorrectionSeconds(): Long {
        val milliseconds = endTime.time - initialEndTime
        return TimeUnit.MILLISECONDS.toSeconds(milliseconds)
    }

    private fun changeEndTime(minutesAmount: Int) {
        calendar.time = endTime
        calendar.add(Calendar.MINUTE, minutesAmount)
        endTimeChanged(calendar.time)
    }

    override fun onDestroy() {
        disposeManager.onDestroy()
    }
}