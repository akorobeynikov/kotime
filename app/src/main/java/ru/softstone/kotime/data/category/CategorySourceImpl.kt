package ru.softstone.kotime.data.category

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.domain.category.CategorySource
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.category.model.CategoryStatus
import javax.inject.Inject

class CategorySourceImpl @Inject constructor(private val categoryDao: CategoryDao) : CategorySource {
    override fun addCategory(name: String): Completable {
        val entry = CategoryEntry(0, name, true)
        return categoryDao.insertAll(entry)
    }

    override fun getCategoryStatusByName(name: String): Single<CategoryStatus> {
        return categoryDao.findByName(name)
            .map { CategoryStatus(it.uid, it.active, exist = true) }
            .toSingle(CategoryStatus(0, active = false, exist = false))
    }

    override fun observeActiveCategories(): Flowable<List<Category>> {
        return categoryDao.getAllActive().map { entries -> entries.map { Category(it.uid, it.name) } }
    }

    override fun setStatus(categoryId: Int, active: Boolean): Completable {
        return Completable.defer {
            categoryDao.setStatus(categoryId, active)
                .flatMapCompletable { affectedRowsCount ->
                    if (affectedRowsCount == 1)
                        Completable.complete()
                    else
                        Completable.error(IllegalStateException("No entry was changed"))
                }
        }
    }
}