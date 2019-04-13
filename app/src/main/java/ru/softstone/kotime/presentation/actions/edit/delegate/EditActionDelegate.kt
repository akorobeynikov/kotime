package ru.softstone.kotime.presentation.actions.edit.delegate

import io.reactivex.Completable
import io.reactivex.Single
import ru.softstone.kotime.architecture.presentation.BasePresenterDelegate
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.model.Action
import ru.softstone.kotime.domain.action.state.EditActionState
import ru.softstone.kotime.presentation.actions.edit.delegate.di.ActionDelegateScope
import ru.softstone.kotime.presentation.actions.edit.model.ActionScreenData
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

@ActionDelegateScope
class EditActionDelegate @Inject constructor(
    private val router: Router,
    private val state: EditActionState?,
    private val actionInteractor: ActionInteractor
) : BasePresenterDelegate(), ActionPresenterDelegate {
    override fun getActionScreenData(): Single<ActionScreenData> {
        if (state == null) {
            return Single.error(IllegalStateException("EditActionState provided as null"))
        }
        return actionInteractor.getAction(state.actionId).map {
            ActionScreenData(it.categoryId, it.description, Date(it.startTime), Date(it.endTime))
        }
    }

    override fun saveAction(categoryId: Int, description: String, startTime: Date, endTime: Date): Completable {
        if (state == null) {
            return Completable.error(IllegalStateException("EditActionState provided as null"))
        }
        val action = Action(state.actionId, categoryId, startTime.time, endTime.time, description)
        return actionInteractor.updateActiveAction(action)
    }

    override fun needDuration() = false

    override fun navigateNext() {
        router.exit()
    }

    override fun checkOverlap(startTime: Date, endTime: Date): Single<Boolean> {
        if (state == null) {
            return Single.error(IllegalStateException("EditActionState provided as null"))
        }
        return actionInteractor.checkOverlap(startTime, endTime, state.actionId)
    }
}