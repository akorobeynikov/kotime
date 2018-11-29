package ru.softstone.kotime.architecture.presentation

import com.arellomobile.mvp.MvpAppCompatActivity
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseActivity<Presenter : BasePresenter<*>> : MvpAppCompatActivity() {
    @Inject
    lateinit var presenterProvider: Provider<Presenter>

    open fun providePresenter(): Presenter {
        return presenterProvider.get()
    }
}