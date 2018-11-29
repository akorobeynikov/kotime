package ru.softstone.kotime.architecture.presentation

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<V : BaseView> : MvpPresenter<V>() {
    private val disposable = CompositeDisposable()

    fun addDisposable(d: Disposable) {
        disposable.add(d)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}