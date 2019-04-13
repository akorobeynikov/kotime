package ru.softstone.kotime.presentation.actions.edit.delegate

import io.reactivex.Completable
import io.reactivex.Single
import ru.softstone.kotime.architecture.presentation.BasePresenterDelegate
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.state.EditSuggestionState
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.TIMER_SCREEN
import ru.softstone.kotime.presentation.actions.edit.delegate.di.ActionDelegateScope
import ru.softstone.kotime.presentation.actions.edit.model.ActionScreenData
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

@ActionDelegateScope
class EditSuggestionDelegate @Inject constructor(
    private val state: EditSuggestionState?,
    private val actionInteractor: ActionInteractor,
    private val router: Router,
    private val timeInteractor: TimeInteractor
) : BasePresenterDelegate(), ActionPresenterDelegate {
    override fun getActionScreenData(): Single<ActionScreenData> {
        if (state == null) {
            return Single.error(IllegalStateException("EditSuggestionState provided as null"))
        }
        return timeInteractor.getStartTime().map { statTime ->
            val endTime = timeInteractor.getCurrentTime()
            ActionScreenData(state.categoryId, state.description, Date(statTime), Date(endTime))
        }
    }

    override fun saveAction(categoryId: Int, description: String, startTime: Date, endTime: Date): Completable {
        if (state == null) {
            return Completable.error(IllegalStateException("EditSuggestionState provided as null"))
        }
        return actionInteractor.addAction(categoryId, startTime.time, endTime.time, description)
            .andThen(timeInteractor.setStartTime(endTime.time))
    }

    override fun needDuration() = true

    override fun navigateNext() {
        router.newRootScreen(TIMER_SCREEN)
    }

    override fun checkOverlap(startTime: Date, endTime: Date): Single<Boolean> {
        return actionInteractor.checkOverlap(startTime, endTime, null)
    }
}