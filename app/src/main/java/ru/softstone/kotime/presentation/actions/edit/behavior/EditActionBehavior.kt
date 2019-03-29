package ru.softstone.kotime.presentation.actions.edit.behavior

import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.state.EditActionState
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.presentation.actions.edit.EditActionView
import ru.terrakok.cicerone.Router
import java.util.*

class EditActionBehavior(
    private val state: EditActionState,
    private val viewState: EditActionView,
    private val categoryInteractor: CategoryInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val actionInteractor: ActionInteractor,
    private val timeInteractor: TimeInteractor,
    private val router: Router,
    private val logger: Logger
) : ActionBehavior {
    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAddActionClick(description: String, categoryIndex: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun endTimeChanged(date: Date) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startTimeChanged(date: Date) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}