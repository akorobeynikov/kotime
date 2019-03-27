package ru.softstone.kotime.architecture.presentation

import android.os.Parcelable

interface SaveStateProvider {
    fun getStates(): Array<Parcelable>
    fun setStates(stateWrappers: Array<Parcelable>?)
}