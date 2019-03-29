package ru.softstone.kotime.architecture.presentation

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable

open class BasePresenter<V : BaseView> : MvpPresenter<V>() {
    private val disposeManager = DisposeManager()

    fun addDisposable(d: Disposable) {
        disposeManager.addDisposable(d)
    }

    override fun onDestroy() {
        disposeManager.onDestroy()
        super.onDestroy()
    }
}