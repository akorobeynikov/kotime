package ru.softstone.kotime.architecture.presentation

import io.reactivex.disposables.Disposable

//todo переиметовать
interface PresenterDelegate {
    fun onDestroy()
    fun addDisposable(d: Disposable)
}
