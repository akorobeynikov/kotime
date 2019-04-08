package ru.softstone.kotime.presentation.actions.edit.behavior

import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.DisposeManager
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.actions.edit.ActionView
import ru.terrakok.cicerone.Router
import java.util.*

class AddActionBehavior(
    private val viewState: ActionView,
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val actionInteractor: ActionInteractor,
    private val timeInteractor: TimeInteractor,
    private val router: Router,
    private val logger: Logger
) : ActionBehavior {
    private val disposeManager = DisposeManager()

    private lateinit var startTime: Date
    private lateinit var endTime: Date

    private lateinit var categories: List<Category>
    private var selectedCategoryIndex = 0

    override fun start() {
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
                    viewState.showSelectedCategory(categories.firstOrNull()?.name ?: "")
                }, {
                    logger.error("Can't observe active categories", it)
                })
        )
    }

    override fun onCategoryClick() {
        //todo может успасть, если пользователь кликнет до получения списка категорий
        viewState.showCategories(categories.map { it.name })
    }

    override fun onCategorySelected(index: Int) {
        selectedCategoryIndex = index
        viewState.showSelectedCategory(categories[selectedCategoryIndex].name)
    }

    private fun showTime() {
        setStartTime(Date(timeInteractor.getCurrentTime()))
        setEndTime(Date(timeInteractor.getCurrentTime()))
    }

    private fun setStartTime(date: Date) {
        startTime = date
        viewState.showStartTime(date)
    }

    private fun setEndTime(date: Date) {
        endTime = date
        viewState.showEndTime(date)
    }


    override fun startTimeChanged(date: Date) {
        //todo сделать прокерку на пересечение т.п.
        setStartTime(date)
    }

    override fun endTimeChanged(date: Date) {
        //todo сделать прокерку на пересечение т.п.
        setEndTime(date)
    }

    override fun onAddActionClick(description: String) {
        val categoryId = categories[selectedCategoryIndex].id
        disposeManager.addDisposable(
            actionInteractor.addAction(categoryId, startTime.time, endTime.time, description)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    logger.debug("Action added")
                    router.exit()
                }, {
                    logger.error("Can't add action", it)
                })
        )
    }

    override fun onMinusDurationClick() {
        //todo частитчно реализовано в EditSuggestionBehavior
    }

    override fun onPlusDurationClick() {
        //todo частитчно реализовано в EditSuggestionBehavior
    }

    override fun onDestroy() {
        disposeManager.onDestroy()
    }
}