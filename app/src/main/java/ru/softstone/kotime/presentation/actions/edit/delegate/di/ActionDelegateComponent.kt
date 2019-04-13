package ru.softstone.kotime.presentation.actions.edit.delegate.di

import dagger.Subcomponent
import ru.softstone.kotime.presentation.actions.edit.delegate.ActionPresenterDelegate

@Subcomponent(modules = [ActionDelegateModule::class])
@ActionDelegateScope
abstract class ActionDelegateComponent {
    abstract fun getActionPresenterDelegate(): ActionPresenterDelegate
}