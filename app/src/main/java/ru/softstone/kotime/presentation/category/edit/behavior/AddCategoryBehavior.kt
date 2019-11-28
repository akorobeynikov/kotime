package ru.softstone.kotime.presentation.category.edit.behavior

import android.graphics.Color
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.DisposeManager
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.CategoryGoalType
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.softstone.kotime.presentation.ERROR_SCREEN
import ru.softstone.kotime.presentation.category.edit.CategoryView
import ru.terrakok.cicerone.Router

class AddCategoryBehavior(
    private val viewState: CategoryView,
    private val categoryInteractor: CategoryInteractor,
    private val errorInteractor: ErrorInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val router: Router,
    private val logger: Logger
) : CategoryBehavior {
    private val disposeManager = DisposeManager()
    private var color:Int = Color.GRAY

    override fun start() {
        viewState.showGoalType(CategoryGoalType.NONE)
    }

    override fun onNextClick(categoryName: String, categoryGoalType: CategoryGoalType) {
        disposeManager.addDisposable(
            categoryInteractor.addCategory(categoryName, categoryGoalType, color)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    {
                        logger.debug("Category added $it")
                        router.exit()
                    },
                    {
                        val wtf = "Can't add category"
                        logger.error(wtf, it)
                        errorInteractor.setLastError(wtf, it)
                        router.navigateTo(ERROR_SCREEN)
                    }
                )
        )
    }

    override fun setCategoryColor(color: Int) {
        this.color = color
    }

    override fun onDestroy() {
        disposeManager.onDestroy()
    }
}