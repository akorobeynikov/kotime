package ru.softstone.kotime.presentation.error

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView

interface ErrorView : BaseView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun sendEmail(body: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(text: String)
}
