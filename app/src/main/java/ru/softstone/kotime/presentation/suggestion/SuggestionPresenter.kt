package ru.softstone.kotime.presentation.suggestion

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.state.EditSuggestionState
import ru.softstone.kotime.domain.suggestion.SuggestionInteractor
import ru.softstone.kotime.presentation.EDIT_ACTION_SCREEN
import ru.softstone.kotime.presentation.suggestion.model.Suggestion
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SuggestionPresenter @Inject constructor(
    private val logger: Logger,
    private val router: Router,
    private val suggestionInteractor: SuggestionInteractor,
    private val actionInteractor: ActionInteractor,
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
                            suggestion.categoryId
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
                            suggestion.categoryId
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

    fun onSuggestionClick(categoryId: Int, description: String) {
        saveAction(categoryId, description)
    }

    fun onEditClick(categoryId: Int, description: String) {
        actionInteractor.setActionState(EditSuggestionState(description, categoryId))
        router.navigateTo(EDIT_ACTION_SCREEN)
    }

    private fun saveAction(categoryId: Int, description: String) {
        addDisposable(
            actionInteractor.addAction(description, categoryId)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe(
                    {
                        logger.debug("Action was added")
                        router.exit()
                    },
                    { logger.error("Can't log an action", it) }
                )
        )
    }

    fun navigateBack() {
        router.exit()
    }
}