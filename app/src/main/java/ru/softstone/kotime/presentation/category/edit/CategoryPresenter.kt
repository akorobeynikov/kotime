package ru.softstone.kotime.presentation.category.edit

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CategoryPresenter @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val router: Router,
    private val logger: Logger
) : BasePresenter<CategoryView>() {

    fun onDoneClick(categoryName: String) {
        addDisposable(
            categoryInteractor.addCategory(categoryName)
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
}