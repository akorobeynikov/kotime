package ru.softstone.kotime.data.storage

import android.annotation.SuppressLint
import android.content.Context
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.data.SchedulerProvider
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.domain.category.CategorySource
import javax.inject.Inject

class DatabasePopulater @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val categorySource: CategorySource,
    private val logger: Logger,
    private val context: Context

) {
    @SuppressLint("CheckResult")
    fun populateCategories() {
        val categories = context.resources
            .getStringArray(R.array.default_categories).asList()
        categorySource.addCategories(categories)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.mainScheduler())
            .subscribe({
                logger.debug("Categories populated")
            }, {
                logger.error("Can't populate categories", it)
            })
    }
}