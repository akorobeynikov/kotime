package ru.softstone.kotime.presentation.actions

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView
import ru.softstone.kotime.domain.action.model.ActionAndCategory

interface ActionsView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showActions(actions: List<ActionAndCategory>)
}
