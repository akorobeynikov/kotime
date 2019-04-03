package ru.softstone.kotime.presentation.category.list.rv

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R
import ru.softstone.kotime.domain.category.model.CategoryGoalType

@EpoxyModelClass(layout = R.layout.item_category)
abstract class CategoryItemModel : EpoxyModelWithHolder<CategoryViewHolder>() {
    @EpoxyAttribute
    lateinit var name: String
    @EpoxyAttribute
    lateinit var goal: CategoryGoalType
    @EpoxyAttribute
    lateinit var deleteListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: CategoryViewHolder) {
        val goalStringId = when (goal) {
            CategoryGoalType.NONE -> R.string.none_goal
            CategoryGoalType.INCREASE -> R.string.increase_goal
            CategoryGoalType.DECREASE -> R.string.decrease_goal
        }
        holder.goalView.setText(goalStringId)
        holder.nameView.text = name
        holder.deleteButton.setOnClickListener(deleteListener)
        holder.container.setOnClickListener(clickListener)
    }
}

