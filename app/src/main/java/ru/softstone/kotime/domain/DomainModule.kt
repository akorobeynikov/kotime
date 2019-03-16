package ru.softstone.kotime.domain

import dagger.Binds
import dagger.Module
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.ActionInteractorImpl
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.CategoryInteractorImpl
import ru.softstone.kotime.domain.suggestion.SuggestionInteractor
import ru.softstone.kotime.domain.suggestion.SuggestionInteractorImpl
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.domain.time.TimeInteractorImpl

@Module(includes = [DomainModule.Declarations::class])
class DomainModule {
    @Module
    internal interface Declarations {
        @Binds
        fun bindActionInteractor(interactorImpl: ActionInteractorImpl): ActionInteractor

        @Binds
        fun bindCategoryInteractor(interactorImpl: CategoryInteractorImpl): CategoryInteractor

        @Binds
        fun bindTimeInteractor(interactorImpl: TimeInteractorImpl): TimeInteractor

        @Binds
        fun bindSuggestionInteractor(interactorImpl: SuggestionInteractorImpl): SuggestionInteractor
    }
}