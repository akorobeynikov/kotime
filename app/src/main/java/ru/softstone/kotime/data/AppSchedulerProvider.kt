package ru.softstone.kotime.data

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.softstone.kotime.architecture.data.SchedulerProvider
import javax.inject.Inject

class AppSchedulerProvider @Inject constructor() : SchedulerProvider {
    override fun mainScheduler() = AndroidSchedulers.mainThread()!!

    override fun ioScheduler() = Schedulers.io()
}