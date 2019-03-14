package ru.softstone.kotime.presentation.log

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView
import ru.softstone.kotime.domain.action.model.ActionAndCategory

interface LogView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showActions(actions: List<ActionAndCategory>)
}
