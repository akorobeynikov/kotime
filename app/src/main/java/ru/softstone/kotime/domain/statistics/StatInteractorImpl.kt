package ru.softstone.kotime.domain.statistics

import io.reactivex.Single
import ru.softstone.kotime.domain.action.ActionSource
import ru.softstone.kotime.domain.category.model.CategoryGoalType
import ru.softstone.kotime.domain.statistics.model.CategoryStatItem
import ru.softstone.kotime.domain.statistics.model.GoalStatItem
import ru.softstone.kotime.domain.statistics.model.Stats
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class StatInteractorImpl @Inject constructor(private val actionSource: ActionSource) : StatInteractor {
    override fun getStatistics(date: Date): Single<Stats> {
        // todo добавить настройки времени смены дня
        // todo вынести получение таймстампов
        val calendar = Calendar.getInstance()
        calendar.time = date
        if (calendar.get(Calendar.HOUR_OF_DAY) < 4) {
            calendar.add(Calendar.DATE, -1)
        }
        calendar.set(Calendar.HOUR_OF_DAY, 4)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis
        calendar.add(Calendar.DATE, 1)
        val endTime = calendar.timeInMillis

        return actionSource.observeActions(startTime, endTime).firstOrError().map { actions ->
            var totalDuration: Long = 0
            val categoriesStatsMap = mutableMapOf<String, Long>()
            val goalStatsMap = mutableMapOf<CategoryGoalType, Long>()
            actions.forEach {
                // изменяем время активности на случай, если активность выходит за пределы времени за которое необходимо получить статистику
                val start = if (it.startTime < startTime) startTime else it.startTime
                val end = if (it.endTime > endTime) endTime else it.endTime

                val actionDuration = end - start
                val categoryDurationSum = categoriesStatsMap[it.categoryName] ?: 0
                categoriesStatsMap[it.categoryName] = categoryDurationSum + actionDuration
                val goalDurationSum = goalStatsMap[it.goalType] ?: 0
                goalStatsMap[it.goalType] = goalDurationSum + actionDuration
                totalDuration += actionDuration
            }
            val categoryStats = categoriesStatsMap
                .map { CategoryStatItem(it.key, it.value, (it.value.toFloat() / totalDuration * 100).roundToInt()) }
                .sortedByDescending { it.duration }
            val goalStats = goalStatsMap
                .map { GoalStatItem(it.key, it.value, (it.value.toFloat() / totalDuration * 100).roundToInt()) }
                .sortedByDescending { it.duration }
            return@map Stats(categoryStats, goalStats, totalDuration)
        }

    }
}