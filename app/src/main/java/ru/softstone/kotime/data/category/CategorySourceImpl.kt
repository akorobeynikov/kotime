package ru.softstone.kotime.data.category

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ru.softstone.kotime.data.storage.AppDatabase
import ru.softstone.kotime.domain.category.CategorySource
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.category.model.CategoryStatus
import ru.softstone.kotime.domain.category.model.PositionOfCategory
import javax.inject.Inject

class CategorySourceImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val appDatabase: AppDatabase
) : CategorySource {
    override fun addCategory(name: String): Completable {
        return categoryDao.getCount().flatMapCompletable { count ->
            val entry = CategoryEntry(0, name, position = count, active = true)
            categoryDao.insertAll(entry)
        }
    }

    override fun addCategories(categoryNames: List<String>): Completable {
        return categoryDao.getCount().flatMapCompletable { count ->
            val entries = categoryNames
                .mapIndexed { index, name -> CategoryEntry(0, name, count + index, true) }
            categoryDao.insertAll(entries)
        }
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
        return categoryDao.setStatus(categoryId, active)
            .flatMapCompletable { affectedRowsCount ->
                if (affectedRowsCount == 1)
                    Completable.complete()
                else
                    Completable.error(IllegalStateException("No entry was changed"))
            }
            .andThen(
                if (active) {
                    categoryDao.getCount()
                        .flatMapCompletable { count ->
                            categoryDao.updatePosition(categoryId, count)

                        }
                } else {
                    categoryDao.getAllActive().firstOrError()
                        .flatMapCompletable { categories ->
                            updateAllPositions(categories.mapIndexed { index, category ->
                                PositionOfCategory(
                                    category.uid,
                                    index
                                )
                            }.toSet())
                        }
                }
            )
    }

    @Suppress("DEPRECATION")
    override fun updateAllPositions(positions: Set<PositionOfCategory>): Completable {
        return Observable.fromIterable(positions)
            .flatMapCompletable { categoryDao.updatePosition(it.id, it.position) }
            .doOnSubscribe { appDatabase.beginTransaction() }
            .doOnComplete { appDatabase.setTransactionSuccessful() }
            .doFinally { appDatabase.endTransaction() }
    }

    override fun getCount(): Single<Int> = categoryDao.getCount()
}