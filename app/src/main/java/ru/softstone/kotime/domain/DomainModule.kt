package ru.softstone.kotime.domain

import dagger.Binds
import dagger.Module
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.ActionInteractorImpl

@Module(includes = [DomainModule.Declarations::class])
class DomainModule {
    @Module
    internal interface Declarations {
        @Binds
        fun bindActionInteractor(interactorImpl: ActionInteractorImpl): ActionInteractor
    }
}