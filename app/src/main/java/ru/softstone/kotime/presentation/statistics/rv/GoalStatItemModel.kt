package ru.softstone.kotime.presentation.statistics.rv

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R
import ru.softstone.kotime.domain.category.model.CategoryGoalType
import ru.softstone.kotime.presentation.getFormattedDuration
import java.util.concurrent.TimeUnit

@EpoxyModelClass(layout = R.layout.item_stat)
abstract class GoalStatItemModel : EpoxyModelWithHolder<StatViewHolder>() {
    @EpoxyAttribute
    var percentage: Int = 0

    @EpoxyAttribute
    var time: Long = 0

    @EpoxyAttribute
    lateinit var goal: CategoryGoalType

    override fun bind(holder: StatViewHolder) {
        val context = holder.categoryView.context
        holder.percentageView.text = context.getString(R.string.precentage, percentage)
        holder.timeView.text = getFormattedDuration(TimeUnit.MILLISECONDS.toSeconds(time).toInt())
        val goalStringId = when (goal) {
            CategoryGoalType.NONE -> R.string.none_goal
            CategoryGoalType.INCREASE -> R.string.increase_goal
            CategoryGoalType.DECREASE -> R.string.decrease_goal
        }
        holder.categoryView.setText(goalStringId)
    }
}

