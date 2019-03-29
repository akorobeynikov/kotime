package ru.softstone.kotime.architecture.presentation

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposeManager {
    private val disposable = CompositeDisposable()

    fun addDisposable(d: Disposable) {
        disposable.add(d)
    }

    fun onDestroy() {
        disposable.dispose()
    }
}