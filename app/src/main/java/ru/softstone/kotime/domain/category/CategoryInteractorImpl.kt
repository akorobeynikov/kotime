package ru.softstone.kotime.domain.category

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.domain.category.model.AddResult
import ru.softstone.kotime.domain.category.model.Category
import javax.inject.Inject

class CategoryInteractorImpl @Inject constructor(private var categorySource: CategorySource) : CategoryInteractor {
    override fun addCategory(name: String): Single<AddResult> {
        return categorySource.getCategoryStatusByName(name)
            .flatMap { status ->
                if (status.exist) {
                    if (status.active) {
                        Single.just(AddResult.ALREADY_EXIST)
                    } else {
                        categorySource.setStatus(status.id, true)
                            .toSingleDefault(AddResult.ENABLED)
                    }
                } else {
                    categorySource.addCategory(name)
                        .toSingleDefault(AddResult.ADDED)
                }
            }
    }

    override fun observeActiveCategories(): Flowable<List<Category>> {
        return categorySource.observeActiveCategories()
    }

    override fun disableCategory(categoryId: Int): Completable {
        return categorySource.setStatus(categoryId, false)
    }
}