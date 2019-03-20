package ru.softstone.kotime.presentation.actions.edit

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.suggestion.SuggestionInteractor
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.TIMER_SCREEN
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

@InjectViewState
class EditActionPresenter @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val suggestionInteractor: SuggestionInteractor,
    private val actionInteractor: ActionInteractor,
    private val timeInteractor: TimeInteractor,
    private val router: Router,
    private val logger: Logger
) : BasePresenter<EditActionView>() {
    private var initialStartTime: Long = 0
    private var initialEndTime: Long = 0

    private lateinit var startTime: Date
    private lateinit var endTime: Date

    private lateinit var categories: List<Category>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        showCategories()
        showTime()
    }

    private fun showCategories() {
        addDisposable(
            categoryInteractor.observeActiveCategories()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    categories = it
                    viewState.showCategories(it.map { category -> category.name })
                    val selectedCategoryId = suggestionInteractor.getSelectedSuggection().categoryId
                    val index = it.indexOfFirst { category -> category.id == selectedCategoryId }
                    viewState.setSelectedCategory(index)
                }, {
                    logger.error("Can't observe active categories", it)
                })
        )
        val description = suggestionInteractor.getSelectedSuggection().description
        viewState.setDescription(description)
    }

    private fun showTime() {
        addDisposable(
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

    fun startTimeChanged(date: Date) {
        val limitedTime = limitInInitialRange(date)
        setStartTime(limitedTime)
    }

    fun endTimeChanged(date: Date) {
        val limitedTime = limitInInitialRange(date)
        setEndTime(limitedTime)
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

    fun onAddActionClick(description: String, categoryIndex: Int) {
        val categoryId = categories[categoryIndex].id
        addDisposable(
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
}