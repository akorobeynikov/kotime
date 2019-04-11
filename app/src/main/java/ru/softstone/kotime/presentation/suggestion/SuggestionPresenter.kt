package ru.softstone.kotime.presentation.suggestion

import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.state.EditSuggestionState
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.softstone.kotime.domain.suggestion.SuggestionInteractor
import ru.softstone.kotime.presentation.EDIT_ACTION_SCREEN
import ru.softstone.kotime.presentation.ERROR_SCREEN
import ru.softstone.kotime.presentation.suggestion.model.Suggestion
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SuggestionPresenter @Inject constructor(
    private val logger: Logger,
    private val router: Router,
    private val suggestionInteractor: SuggestionInteractor,
    private val actionInteractor: ActionInteractor,
    private val errorInteractor: ErrorInteractor,
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
                    val wtf = "can't get general suggestions"
                    logger.error(wtf, it)
                    errorInteractor.setLastError(wtf, it)
                    router.navigateTo(ERROR_SCREEN)
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
                    val wtf = "Can't get suggestions"
                    logger.error(wtf, it)
                    errorInteractor.setLastError(wtf, it)
                    router.navigateTo(ERROR_SCREEN)
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

    fun onFastRecordClick(categoryId: Int, description: String) {
        saveAction(categoryId, description)
    }

    fun onSuggestionClick(description: String) {
        viewState.setSuggestionDescription(description)

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
                    {
                        val wtf = "Can't add action (suggestions)"
                        logger.error(wtf, it)
                        errorInteractor.setLastError(wtf, it)
                        router.navigateTo(ERROR_SCREEN)
                    }
                )
        )
    }

    fun navigateBack() {
        router.exit()
    }

}