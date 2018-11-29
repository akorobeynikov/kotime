package ru.softstone.kotime.architecture.presentation

import com.arellomobile.mvp.MvpAppCompatFragment
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseFragment<Presenter : BasePresenter<*>> : MvpAppCompatFragment() {
    @Inject
    lateinit var presenterProvider: Provider<Presenter>

    open fun providePresenter(): Presenter {
        return presenterProvider.get()
    }
}