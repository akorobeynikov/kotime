package ru.softstone.kotime.presentation.actions.edit.behavior

import io.reactivex.functions.BiFunction
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.DisposeManager
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.model.Action
import ru.softstone.kotime.domain.action.state.EditActionState
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.ERROR_SCREEN
import ru.softstone.kotime.presentation.actions.edit.ActionView
import ru.terrakok.cicerone.Router
import java.util.*

class EditActionBehavior(
    private val state: EditActionState,
    private val viewState: ActionView,
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val actionInteractor: ActionInteractor,
    private val timeInteractor: TimeInteractor,//удалить если не пригодится
    private val errorInteractor: ErrorInteractor,
    private val router: Router,
    private val logger: Logger
) : ActionBehavior {
    private val disposeManager = DisposeManager()
    private lateinit var categories: List<Category>
    private var selectedCategoryIndex = 0

    private lateinit var startTime: Date
    private lateinit var endTime: Date

    override fun start() {
        disposeManager.addDisposable(
            categoryInteractor.observeActiveCategories().firstOrError()
                .zipWith(
                    actionInteractor.getAction(state.actionId),
                    BiFunction<List<Category>, Action, Pair<List<Category>, Action>> { categories, action -> categories to action })
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    val categories = it.first
                    val action = it.second
                    this.categories = categories

                    val selectedCategoryId = action.categoryId
                    //todo упадет если категория будет неактивная
                    selectedCategoryIndex = categories.indexOfFirst { category -> category.id == selectedCategoryId }
                    viewState.showSelectedCategory(categories[selectedCategoryIndex].name)
                    viewState.setDescription(action.description)
                    setStartTime(Date(action.startTime))
                    setEndTime(Date(action.endTime))
                }, {
                    val wtf = "Can't get categories or an action"
                    logger.error(wtf, it)
                    errorInteractor.setLastError(wtf, it)
                    router.navigateTo(ERROR_SCREEN)
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

    private fun setStartTime(date: Date) {
        startTime = date
        viewState.showStartTime(date)
    }

    private fun setEndTime(date: Date) {
        endTime = date
        viewState.showEndTime(date)
    }

    override fun onAddActionClick(description: String) {
        val categoryId = categories[selectedCategoryIndex].id
        val action = Action(state.actionId, categoryId, startTime.time, endTime.time, description)
        disposeManager.addDisposable(
            actionInteractor.updateActiveAction(action)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    logger.debug("Action updated")
                    router.exit()
                }, {
                    val wtf = "Can't update action"
                    logger.error(wtf, it)
                    errorInteractor.setLastError(wtf, it)
                    router.navigateTo(ERROR_SCREEN)
                })
        )
    }

    override fun startTimeChanged(date: Date) {
        //todo сделать прокерку на пересечение т.п.
        setStartTime(date)
    }

    override fun endTimeChanged(date: Date) {
        //todo сделать прокерку на пересечение т.п.
        setEndTime(date)
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