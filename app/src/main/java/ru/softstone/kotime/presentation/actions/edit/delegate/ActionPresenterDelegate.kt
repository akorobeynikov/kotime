package ru.softstone.kotime.presentation.actions.edit.delegate

import io.reactivex.Completable
import io.reactivex.Single
import ru.softstone.kotime.presentation.actions.edit.model.ActionScreenData
import java.util.*

interface ActionPresenterDelegate {
    fun getActionScreenData(): Single<ActionScreenData>
    fun saveAction(categoryId: Int, description: String, startTime: Date, endTime: Date): Completable
    fun needDuration(): Boolean
    fun navigateNext()
    fun checkOverlap(startTime: Date, endTime: Date): Single<Boolean>
}