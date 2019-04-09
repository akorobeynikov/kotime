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
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: CategoryViewHolder) {
        val imageResourceId = when (goal) {
            CategoryGoalType.NONE -> 0
            CategoryGoalType.INCREASE -> R.drawable.ic_trending_up
            CategoryGoalType.DECREASE -> R.drawable.ic_trending_down
        }
        holder.goalView.setImageResource(imageResourceId)
        holder.nameView.text = name
        holder.container.setOnClickListener(clickListener)
    }
}

