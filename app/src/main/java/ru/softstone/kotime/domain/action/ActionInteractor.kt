package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import ru.softstone.kotime.domain.category.model.Category

interface ActionInteractor {
    fun addAction(description: String, category: Category): Completable
}