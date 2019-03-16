package ru.softstone.kotime.presentation.timer

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.CATEGORY_SCREEN
import ru.softstone.kotime.presentation.LOG_SCREEN
import ru.softstone.kotime.presentation.SUGGESTION_SCREEN
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class TimerPresenter @Inject constructor(
    private val router: Router,
    private val actionInteractor: ActionInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val categoryInteractor: CategoryInteractor,
    private val timeInteractor: TimeInteractor,
    private val logger: Logger
) : BasePresenter<TimerView>() {
    private var startTime = 0L
    private var categories: List<Category>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        startTime = System.currentTimeMillis()
        updateTime(0)
        addDisposable(
            Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .concatMapSingle { timeInteractor.getTimeFromStart() }
                .subscribe { updateTime(it) }
        )
        addDisposable(
            categoryInteractor.observeActiveCategories()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    categories = it
                    viewState.showCategories(it.map { it.name })
                }, {
                    logger.error("Can't observe categories", it)
                })
        )
    }

    private fun updateTime(timeFromStart: Long) {
        val diffSeconds = TimeUnit.MILLISECONDS.toSeconds(timeFromStart)
        viewState.showTime(diffSeconds.toInt())
    }

    fun onShowLogsClick() {
        router.navigateTo(LOG_SCREEN)
    }

    fun onRecordClick(description: String, selectedCategoryPosition: Int) {
        val categories = categories
        if (categories != null && categories.size > selectedCategoryPosition) {
            val category = categories[selectedCategoryPosition]
            addDisposable(
                actionInteractor.addAction(description, category)
                    .subscribeOn(schedulerProvider.ioScheduler())
                    .observeOn(schedulerProvider.mainScheduler())
                    .subscribe(
                        {
                            logger.debug("Action was added")
                            updateTime(0)
                        },
                        { logger.error("Can't log an action", it) }
                    )
            )
        } else {
            logger.error("Can't find category")
        }
    }

    fun onShowCategoriesClick() {
        router.navigateTo(CATEGORY_SCREEN)
    }

    fun onTimerClick() {
        addDisposable(
            timeInteractor.isStopped()
                .flatMapCompletable { timerIsStopped ->
                    if (timerIsStopped) {
                        timeInteractor.resetTimer()
                    } else {
                        timeInteractor.stopTimer()
                    }
                }
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    {
                        updateTime(0)
                    },
                    { logger.error("Can't get the timer state", it) }
                )
        )
    }

    fun onAddRecordClick() {
        router.navigateTo(SUGGESTION_SCREEN)
    }
}