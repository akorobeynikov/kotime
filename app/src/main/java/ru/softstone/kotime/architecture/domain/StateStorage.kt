package ru.softstone.kotime.architecture.domain

import android.os.Parcelable
import io.reactivex.Single

interface StateStorage {
    fun put(key: String, state: Parcelable)
    fun <T> pull(key: String): Single<T>
}