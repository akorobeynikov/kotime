package ru.softstone.kotime.domain.suggestion

import io.reactivex.Single
import ru.softstone.kotime.domain.suggestion.model.Suggestion

interface SuggestionInteractor {
    fun getSuggestionsFor(input: String): Single<List<Suggestion>>
    fun getGeneralSuggestions(): Single<List<Suggestion>>
}