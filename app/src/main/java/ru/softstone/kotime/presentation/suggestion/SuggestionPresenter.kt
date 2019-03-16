package ru.softstone.kotime.presentation.suggestion

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.suggestion.SuggestionInteractor
import ru.softstone.kotime.presentation.suggestion.model.Suggestion
import javax.inject.Inject

@InjectViewState
class SuggestionPresenter @Inject constructor(
    private val logger: Logger,
    private val suggestionInteractor: SuggestionInteractor,
    private val schedulerProvider: SchedulerProvider
) : BasePresenter<SuggestionView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        showGeneralSuggestions()
    }

    private fun showGeneralSuggestions() {
        addDisposable(
            suggestionInteractor.getGeneralSuggestions()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.showSuggestions(it.map { suggestion ->
                        Suggestion(
                            suggestion.id,
                            suggestion.description,
                            suggestion.categoryName,
                            "time"
                        )
                    })
                }, {
                    logger.error("can't get general suggestions", it)
                })
        )
    }

    private fun showSuggestionsFor(description: String) {
        addDisposable(
            suggestionInteractor.getSuggestionsFor(description)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe({
                    viewState.showSuggestions(it.map { suggestion ->
                        Suggestion(
                            suggestion.id,
                            suggestion.description,
                            suggestion.categoryName,
                            "time"
                        )
                    })
                }, {
                    logger.error("can't get suggestions", it)
                })
        )
    }

    fun onDescriptionChanged(description: String) {
        if (description.isBlank()) {
            showGeneralSuggestions()
        } else {
            showSuggestionsFor(description)
        }
    }

    fun onSuggestionClick() {
        logger.debug("onSuggestionClick")
    }


}