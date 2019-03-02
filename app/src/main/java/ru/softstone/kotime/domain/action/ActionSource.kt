package ru.softstone.kotime.domain.action

import io.reactivex.Completable

interface ActionSource {
    fun addAction(categoryId: Int, startTime: Long, endTime: Long, description: String): Completable
}