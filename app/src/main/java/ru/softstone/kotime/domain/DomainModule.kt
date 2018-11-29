package ru.softstone.kotime.domain

import dagger.Module

@Module(includes = [DomainModule.Declarations::class])
class DomainModule {
    @Module
    internal interface Declarations {
//        @Binds
//        fun bindDemoInteractor(interactorImpl: DemoInteractorImpl): DemoInteractor
    }
}