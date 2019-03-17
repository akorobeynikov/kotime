package ru.softstone.kotime.presentation.actions

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import java.util.*
import javax.inject.Inject

@InjectViewState
class ActionsPresenter @Inject constructor(
    private val actionInteractor: ActionInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val logger: Logger
) : BasePresenter<ActionsView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        addDisposable(
            actionInteractor.observeActions(Date())
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.showActions(it)
                }, {
                })
        )
    }
}