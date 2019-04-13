package ru.softstone.kotime.presentation.actions.edit.delegate.di

import ru.softstone.kotime.AppComponent
import ru.softstone.kotime.domain.action.state.ActionState
import ru.softstone.kotime.presentation.actions.edit.delegate.ActionPresenterDelegate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActionDelegateProvider @Inject constructor(private val appComponent: AppComponent) {
    private var instance: ActionPresenterDelegate? = null

    fun getInstance(state: ActionState): ActionPresenterDelegate {
        return instance ?: newInstance(state)
    }

    private fun newInstance(state: ActionState): ActionPresenterDelegate {
        val module = ActionDelegateModule(state)
        val component = appComponent.actionDelegateComponent(module)
        return component.getActionPresenterDelegate()
    }

    fun clearInstance() {
        instance = null
    }
}