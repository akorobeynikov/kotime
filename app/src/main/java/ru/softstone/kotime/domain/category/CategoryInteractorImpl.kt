package ru.softstone.kotime.domain.category

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.architecture.domain.StateStorage
import ru.softstone.kotime.domain.category.model.AddResult
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.category.model.CategoryGoalType
import ru.softstone.kotime.domain.category.model.PositionOfCategory
import ru.softstone.kotime.domain.category.state.CategoryState
import javax.inject.Inject

class CategoryInteractorImpl @Inject constructor(
    private var categorySource: CategorySource,
    private val stateStorage: StateStorage
) : CategoryInteractor {
    companion object {
        private const val CATEGORY_STATE = "CATEGORY_STATE"
    }

    override fun addCategory(name: String, goalType: CategoryGoalType): Single<AddResult> {
        return categorySource.getCategoryStatusByName(name)
            .flatMap { status ->
                if (status.exist) {
                    if (status.active) {
                        categorySource.setType(status.id, goalType).toSingleDefault(AddResult.ALREADY_EXIST)
                    } else {
                        categorySource.setType(status.id, goalType)
                            .andThen(categorySource.setStatus(status.id, true))
                            .toSingleDefault(AddResult.ENABLED)
                    }
                } else {
                    categorySource.addCategory(name, goalType)
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

    override fun updateAllPositions(positions: Set<PositionOfCategory>): Completable {
        return categorySource.updateAllPositions(positions)
    }

    override fun setCategoryState(state: CategoryState) {
        stateStorage.put(CATEGORY_STATE, state)
    }

    override fun getCategoryState(): Single<CategoryState> {
        return stateStorage.pull(CATEGORY_STATE)
    }

    override fun getCategoryById(categoryId: Int): Single<Category> {
        return categorySource.getCategoryById(categoryId)
    }

    override fun updateActiveCategory(category: Category): Completable {
        return categorySource.getCategoryById(category.id).flatMapCompletable {
            if (it.name == category.name) {
                categorySource.setType(category.id, category.goalType)
            } else {
                addCategory(category.name, category.goalType).flatMapCompletable {
                    categorySource.setStatus(category.id, false)
                    //todo сохранять позицую при изменении категирии
                }
            }
        }
    }
}