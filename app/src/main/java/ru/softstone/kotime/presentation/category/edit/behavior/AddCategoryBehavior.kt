package ru.softstone.kotime.presentation.category.edit.behavior

import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.DisposeManager
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.CategoryGoalType
import ru.softstone.kotime.presentation.category.edit.CategoryView
import ru.terrakok.cicerone.Router

class AddCategoryBehavior(
    private val viewState: CategoryView,
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val router: Router,
    private val logger: Logger
) : CategoryBehavior {
    private val disposeManager = DisposeManager()

    override fun start() {
        viewState.showGoalType(CategoryGoalType.NONE)
    }

    override fun onNextClick(categoryName: String, categoryGoalType: CategoryGoalType) {
        disposeManager.addDisposable(
            categoryInteractor.addCategory(categoryName, categoryGoalType)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    {
                        logger.debug("Category added $it")
                        router.exit()
                    },
                    { logger.error("Can't add category", it) }
                )
        )
    }

    override fun onDestroy() {
        disposeManager.onDestroy()
    }
}