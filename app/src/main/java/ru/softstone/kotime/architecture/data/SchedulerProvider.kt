package ru.softstone.kotime.architecture.data

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun mainScheduler(): Scheduler
    fun ioScheduler(): Scheduler
}