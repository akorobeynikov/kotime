package ru.softstone.kotime.architecture.presentation

import io.reactivex.disposables.Disposable

// todo переименовать
open class BasePresenterDelegate : PresenterDelegate {
    private val disposeManager = DisposeManager()

    override fun addDisposable(d: Disposable) {
        disposeManager.addDisposable(d)
    }

    override fun onDestroy() {
        disposeManager.onDestroy()
    }
}