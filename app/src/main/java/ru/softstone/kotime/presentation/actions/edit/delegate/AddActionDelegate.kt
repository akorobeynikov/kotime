package ru.softstone.kotime.presentation.actions.edit.delegate

import io.reactivex.Completable
import io.reactivex.Single
import ru.softstone.kotime.architecture.presentation.BasePresenterDelegate
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.presentation.actions.edit.delegate.di.ActionDelegateScope
import ru.softstone.kotime.presentation.actions.edit.model.ActionScreenData
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

@ActionDelegateScope
class AddActionDelegate @Inject constructor(
    private val router: Router,
    private val actionInteractor: ActionInteractor
) : BasePresenterDelegate(), ActionPresenterDelegate {
    companion object {
        private const val DEFAULT_ACTION_DURATION = 30 //minutes
    }

    override fun getActionScreenData(): Single<ActionScreenData> {
        val calendar = Calendar.getInstance()
        val endTime = calendar.time
        calendar.add(Calendar.MINUTE, -DEFAULT_ACTION_DURATION)
        val startTime = calendar.time
        val data = ActionScreenData(null, null, startTime, endTime)
        return Single.just(data)
    }

    override fun saveAction(categoryId: Int, description: String, startTime: Date, endTime: Date): Completable {
        return actionInteractor.addAction(categoryId, startTime.time, endTime.time, description)
    }

    override fun needDuration() = false

    override fun navigateNext() {
        router.exit()
    }

    override fun checkOverlap(startTime: Date, endTime: Date): Single<Boolean> {
        return actionInteractor.checkOverlap(startTime, endTime, null)
    }
}