package ru.softstone.kotime.architecture.data

import android.os.Parcelable
import io.reactivex.Single
import io.reactivex.subjects.AsyncSubject
import kotlinx.android.parcel.Parcelize
import ru.softstone.kotime.architecture.domain.StateStorage
import ru.softstone.kotime.architecture.presentation.SaveStateProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StateStorageImpl @Inject constructor() : StateStorage, SaveStateProvider {
    private val states = mutableMapOf<String, Parcelable>()
    private var initiated = false
    private val asyncSubject = AsyncSubject.create<Map<String, Parcelable>>()

    override fun put(key: String, state: Parcelable) {
        states[key] = state
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> pull(key: String): Single<T> {
        return Single.defer {
            if (initiated) {
                return@defer Single.just(states[key] as T)
            } else {
                return@defer asyncSubject.map { it[key] as T }.singleOrError()
            }
        }
    }

    override fun setStates(stateWrappers: Array<Parcelable>?) {
        stateWrappers?.forEach { wrapper ->
            if (wrapper is StateWrapper) {
                put(wrapper.key, wrapper.state)
            }
        }
        initiated = true
        asyncSubject.onNext(states)
        asyncSubject.onComplete()
    }

    override fun getStates(): Array<Parcelable> {
        return states.map { StateWrapper(it.key, it.value) }.toTypedArray()
    }
}

@Parcelize
data class StateWrapper(val key: String, val state: Parcelable) : Parcelable