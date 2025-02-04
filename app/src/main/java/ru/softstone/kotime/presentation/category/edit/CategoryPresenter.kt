package ru.softstone.kotime.presentation.category.edit

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.CategoryGoalType
import ru.softstone.kotime.domain.category.state.AddCategoryState
import ru.softstone.kotime.domain.category.state.CategoryState
import ru.softstone.kotime.domain.category.state.EditCategoryState
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.softstone.kotime.presentation.ERROR_SCREEN
import ru.softstone.kotime.presentation.category.edit.behavior.AddCategoryBehavior
import ru.softstone.kotime.presentation.category.edit.behavior.CategoryBehavior
import ru.softstone.kotime.presentation.category.edit.behavior.EditCategoryBehavior
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CategoryPresenter @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val errorInteractor: ErrorInteractor,
    private val router: Router,
    private val logger: Logger
) : BasePresenter<CategoryView>() {
    lateinit var behavior: CategoryBehavior

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        addDisposable(
            categoryInteractor.getCategoryState()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    {
                        behavior = getBehavior(it)
                        behavior.start()
                    },
                    {
                        val wtf = "Can't get state"
                        logger.error(wtf, it)
                        errorInteractor.setLastError(wtf, it)
                        router.navigateTo(ERROR_SCREEN)
                    }
                )
        )
        viewState.enableNextButton(false)
    }

    fun onNextClick(
        categoryName: String,
        selectedGoalType: CategoryGoalType
    ) {
        if (!categoryName.isBlank()) {
            behavior.onNextClick(categoryName.trim(), selectedGoalType)
        }
    }

    private fun getBehavior(state: CategoryState): CategoryBehavior {
        return when (state) {
            // todo инстацировать через dagger
            is AddCategoryState -> AddCategoryBehavior(
                viewState,
                categoryInteractor,
                errorInteractor,
                schedulerProvider,
                router,
                logger
            )
            // todo инстацировать через dagger
            is EditCategoryState -> EditCategoryBehavior(
                state.categoryId,
                viewState,
                categoryInteractor,
                errorInteractor,
                schedulerProvider,
                router,
                logger
            )
            else -> throw IllegalStateException("Unknown type of state")
        }

    }

    fun onBackPressed() {
        router.exit()
    }

    fun onCategoryNameChange(text: String) {
        viewState.enableNextButton(!text.isBlank())
    }

    fun onColorSelected(color: Int) {
        viewState.showColor(color)
        behavior.setCategoryColor(color)
    }
}