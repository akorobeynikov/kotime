package ru.softstone.kotime.presentation.log

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import javax.inject.Inject

@InjectViewState
class LogPresenter @Inject constructor(
    private val actionInteractor: ActionInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val logger: Logger
) : BasePresenter<LogView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        addDisposable(
            actionInteractor.observeActions()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.showActions(it)
                }, {
                })
        )
    }
}