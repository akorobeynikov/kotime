package ru.softstone.kotime.presentation.category

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.category.CategoryInteractor
import javax.inject.Inject

@InjectViewState
class CategoryPresenter @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val logger: Logger
) : BasePresenter<CategoryView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        addDisposable(
            categoryInteractor.observeActiveCategories()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.showCategories(it)
                }, {
                    logger.error("Can't observe categories", it)
                })
        )
    }

    fun onAddCategoryClick(categoryName: String) {
        addDisposable(
            categoryInteractor.addCategory(categoryName)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    { logger.debug("Category was added $it") },
                    { logger.error("Can't add category", it) }
                )
        )
    }

    fun onDeleteCategoryClick(categoryId: Int) {
        addDisposable(
            categoryInteractor.disableCategory(categoryId)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    { logger.debug("Category was disabled") },
                    { logger.error("Can't disable category", it) }
                )
        )
    }
}