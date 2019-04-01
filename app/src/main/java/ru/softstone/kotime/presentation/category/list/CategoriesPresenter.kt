package ru.softstone.kotime.presentation.category.list

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.category.model.PositionOfCategory
import ru.softstone.kotime.presentation.CATEGORY_SCREEN
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CategoriesPresenter @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val router: Router,
    private val logger: Logger
) : BasePresenter<CategoriesView>() {

    private var categories: MutableList<Category>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        addDisposable(
            categoryInteractor.observeActiveCategories()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    this.categories = it.toMutableList()
                    viewState.showCategories(categories!!)
                }, {
                    logger.error("Can't observe categories", it)
                })
        )
    }

    fun onAddCategoryClick() {
        // todo добавить установку состояния
        router.navigateTo(CATEGORY_SCREEN)
    }

    fun onDeleteCategoryClick(categoryId: Int) {
        addDisposable(
            categoryInteractor.disableCategory(categoryId)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    { logger.debug("Category disabled") },
                    { logger.error("Can't disable category", it) }
                )
        )
    }

    fun onCategoryPositionChanged(categoryId: Long, fromPosition: Int, toPosition: Int) {
        logger.debug("onCategoryPositionChanged")
        categories?.let { categories ->
            val categoryIndex = categories.indexOfFirst { it.id.toLong() == categoryId }
            categories.add(categoryIndex + (toPosition - fromPosition), categories.removeAt(categoryIndex))
            viewState.showCategories(categories)
            updateCategoriesPositions(categories)
        }
    }

    private fun updateCategoriesPositions(categories: List<Category>) {
        val positions = categories.mapIndexed { index, category ->
            PositionOfCategory(category.id, index)
        }.toSet()
        addDisposable(
            categoryInteractor.updateAllPositions(positions)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    { logger.debug("Order changed") },
                    { logger.error("Can't update positions of categories", it) }
                )
        )
    }
}