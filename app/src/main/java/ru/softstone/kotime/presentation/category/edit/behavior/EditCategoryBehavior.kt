package ru.softstone.kotime.presentation.category.edit.behavior

import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.DisposeManager
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.category.model.CategoryGoalType
import ru.softstone.kotime.presentation.category.edit.CategoryView
import ru.terrakok.cicerone.Router

class EditCategoryBehavior(
    private val categoryId: Int,
    private val viewState: CategoryView,
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val router: Router,
    private val logger: Logger
) : CategoryBehavior {
    private val disposeManager = DisposeManager()
    override fun start() {
        disposeManager.addDisposable(
            categoryInteractor.getCategoryById(categoryId)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.showCategoryName(it.name)
                    viewState.showGoalType(it.goalType)
                }, {
                    logger.error("Can't get category by id $categoryId", it)
                })
        )
    }

    override fun onNextClick(categoryName: String, categoryGoalType: CategoryGoalType) {
        disposeManager.addDisposable(
            categoryInteractor.updateActiveCategory(Category(categoryId, categoryName, categoryGoalType))
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    router.exit()
                }, {
                    logger.error("Can't update category $categoryId", it)
                })
        )
    }

    override fun onDestroy() {
        disposeManager.onDestroy()
    }
}