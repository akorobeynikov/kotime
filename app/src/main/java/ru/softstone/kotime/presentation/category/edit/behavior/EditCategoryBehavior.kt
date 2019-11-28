package ru.softstone.kotime.presentation.category.edit.behavior

import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.DisposeManager
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.category.model.CategoryGoalType
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.softstone.kotime.presentation.ERROR_SCREEN
import ru.softstone.kotime.presentation.category.edit.CategoryView
import ru.terrakok.cicerone.Router

class EditCategoryBehavior(
    private val categoryId: Int,
    private val viewState: CategoryView,
    private val categoryInteractor: CategoryInteractor,
    private val errorInteractor: ErrorInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val router: Router,
    private val logger: Logger
) : CategoryBehavior {
    private val disposeManager = DisposeManager()
    private var categoryColor = 0
    override fun start() {
        disposeManager.addDisposable(
            categoryInteractor.getCategoryById(categoryId)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.enableNextButton(true)
                    viewState.showCategoryName(it.name)
                    viewState.showGoalType(it.goalType)
                    viewState.showColor(it.color)
                    categoryColor = it.color
                }, {
                    val wtf = "Can't get category by id $categoryId"
                    logger.error(wtf, it)
                    errorInteractor.setLastError(wtf, it)
                    router.navigateTo(ERROR_SCREEN)
                })
        )
    }

    override fun onNextClick(categoryName: String, categoryGoalType: CategoryGoalType) {
        disposeManager.addDisposable(
            categoryInteractor.updateActiveCategory(
                Category(
                    categoryId,
                    categoryName,
                    categoryGoalType,
                    categoryColor
                )
            )
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    router.exit()
                }, {
                    val wtf = "Can't update category $categoryId"
                    logger.error(wtf, it)
                    errorInteractor.setLastError(wtf, it)
                    router.navigateTo(ERROR_SCREEN)
                })
        )
    }

    override fun setCategoryColor(color: Int) {
        this.categoryColor = color
    }

    override fun onDestroy() {
        disposeManager.onDestroy()
    }
}