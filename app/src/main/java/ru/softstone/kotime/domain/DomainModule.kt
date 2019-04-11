package ru.softstone.kotime.domain

import dagger.Binds
import dagger.Module
import ru.softstone.kotime.domain.action.ActionInteractor
import ru.softstone.kotime.domain.action.ActionInteractorImpl
import ru.softstone.kotime.domain.category.CategoryInteractor
import ru.softstone.kotime.domain.category.CategoryInteractorImpl
import ru.softstone.kotime.domain.error.ErrorInteractor
import ru.softstone.kotime.domain.error.ErrorInteractorImpl
import ru.softstone.kotime.domain.statistics.StatInteractor
import ru.softstone.kotime.domain.statistics.StatInteractorImpl
import ru.softstone.kotime.domain.suggestion.SuggestionInteractor
import ru.softstone.kotime.domain.suggestion.SuggestionInteractorImpl
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.domain.time.TimeInteractorImpl
import javax.inject.Singleton

@Module(includes = [DomainModule.Declarations::class])
class DomainModule {
    @Module
    internal interface Declarations {
        @Binds
        @Singleton
        fun bindActionInteractor(interactorImpl: ActionInteractorImpl): ActionInteractor

        @Binds
        @Singleton
        fun bindCategoryInteractor(interactorImpl: CategoryInteractorImpl): CategoryInteractor

        @Binds
        @Singleton
        fun bindTimeInteractor(interactorImpl: TimeInteractorImpl): TimeInteractor

        @Binds
        @Singleton
        fun bindSuggestionInteractor(interactorImpl: SuggestionInteractorImpl): SuggestionInteractor

        @Binds
        @Singleton
        fun bindStatInteractor(interactorImpl: StatInteractorImpl): StatInteractor

        @Binds
        @Singleton
        fun bindErrornteractor(interactorImpl: ErrorInteractorImpl): ErrorInteractor
    }
}