package ru.softstone.kotime.domain.suggestion

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.softstone.kotime.domain.action.ActionSource
import ru.softstone.kotime.domain.action.model.DesriptionAndCategory
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.suggestion.model.Suggestion
import javax.inject.Inject

class SuggestionInteractorImpl @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    private val actionSource: ActionSource
) : SuggestionInteractor {

    override fun getGeneralSuggestions(): Single<List<Suggestion>> {
        return actionSource.getMostFrequent().map { mostFrequent ->
            mostFrequent.mapIndexed { index, it ->
                Suggestion(
                    index.toLong(),
                    it.description,
                    it.categoryId,
                    it.categoryName
                )
            }
        }
    }

    override fun getSuggestionsFor(input: String): Single<List<Suggestion>> {
        return actionSource.getMostFrequentWhere(descriptionStartsWith = input)
            .zipWith(
                categoryInteractor.observeActiveCategories().firstOrError(),
                BiFunction<List<DesriptionAndCategory>, List<Category>, Pair<List<DesriptionAndCategory>, List<Category>>> { t1, t2 ->
                    Pair(
                        t1,
                        t2
                    )
                })
            .map { data ->
                val mostFrequent = data.first
                val categories = data.second
                var id = 0L

                val suggestions = mutableListOf<Suggestion>()
                suggestions.addAll(mostFrequent.map {
                    Suggestion(
                        id++,
                        it.description,
                        it.categoryId,
                        it.categoryName
                    )
                })
                suggestions.addAll(
                    categories
                    .map {
                        Suggestion(id++, input, it.id, it.name)
                    })
                return@map suggestions
            }
    }
}