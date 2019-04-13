package ru.softstone.kotime.presentation.actions.edit.delegate.di

import dagger.Module
import dagger.Provides
import ru.softstone.kotime.domain.action.state.ActionState
import ru.softstone.kotime.domain.action.state.AddActionState
import ru.softstone.kotime.domain.action.state.EditActionState
import ru.softstone.kotime.domain.action.state.EditSuggestionState
import ru.softstone.kotime.presentation.actions.edit.delegate.ActionPresenterDelegate
import ru.softstone.kotime.presentation.actions.edit.delegate.AddActionDelegate
import ru.softstone.kotime.presentation.actions.edit.delegate.EditActionDelegate
import ru.softstone.kotime.presentation.actions.edit.delegate.EditSuggestionDelegate
import javax.inject.Provider


@Module
class ActionDelegateModule(private val actionState: ActionState) {

    @Provides
    @ActionDelegateScope
    fun provideState() = actionState

    @Provides
    @ActionDelegateScope
    fun provideEditActionState(state: ActionState): EditActionState? {
        return state as? EditActionState
    }

    @Provides
    @ActionDelegateScope
    fun provideAddActionState(state: ActionState): AddActionState? {
        return state as? AddActionState
    }

    @Provides
    @ActionDelegateScope
    fun provideEditSuggestionActionState(state: ActionState): EditSuggestionState? {
        return state as? EditSuggestionState
    }


    @Provides
    @ActionDelegateScope
    fun provideActionPresenterDelegate(
        state: ActionState,
        addActionDelegateProvider: Provider<AddActionDelegate>,
        editActionDelegateProvider: Provider<EditActionDelegate>,
        editSuggestionDelegateProvider: Provider<EditSuggestionDelegate>
    ): ActionPresenterDelegate {
        return when (state) {
            is AddActionState -> addActionDelegateProvider.get()
            is EditActionState -> editActionDelegateProvider.get()
            is EditSuggestionState -> editSuggestionDelegateProvider.get()
            else -> throw IllegalStateException("Unknown type of state")
        }
    }
}