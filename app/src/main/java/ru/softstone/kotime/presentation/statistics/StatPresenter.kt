package ru.softstone.kotime.presentation.statistics

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.statistics.StatInteractor
import java.util.*
import javax.inject.Inject

@InjectViewState
class StatPresenter @Inject constructor(
    private val statInteractor: StatInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val logger: Logger
) : BasePresenter<StatView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        addDisposable(
            statInteractor.getStatistics(Date())
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.showStats(it)
                    logger.debug("getStatistics")
                }, {
                    logger.error("Can't get statistics", it)
                })
        )
    }
}