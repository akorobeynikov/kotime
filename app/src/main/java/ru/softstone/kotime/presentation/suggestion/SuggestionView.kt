package ru.softstone.kotime.presentation.suggestion

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView
import ru.softstone.kotime.presentation.suggestion.model.Suggestion

interface SuggestionView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSuggestions(suggestions: List<Suggestion>)
}
