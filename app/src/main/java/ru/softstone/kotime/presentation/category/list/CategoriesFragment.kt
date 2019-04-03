package ru.softstone.kotime.presentation.category.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyTouchHelper
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_categories.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.presentation.category.list.rv.CategoriesRvController
import ru.softstone.kotime.presentation.category.list.rv.CategoryItemModel
import javax.inject.Inject

class CategoriesFragment : BaseFragment<CategoriesPresenter>(),
    CategoriesView {
    companion object {
        fun newInstance() = CategoriesFragment()
    }

    @InjectPresenter
    lateinit var presenter: CategoriesPresenter

    @Inject
    lateinit var rvController: CategoriesRvController

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_category_button.setOnClickListener {
            presenter.onAddCategoryClick()
        }

        EpoxyTouchHelper.initDragging(rvController)
            .withRecyclerView(categories_rv)
            .forVerticalList()
            .withTarget(CategoryItemModel::class.java)
            .andCallbacks(object : EpoxyTouchHelper.DragCallbacks<CategoryItemModel>() {
                override fun onDragStarted(model: CategoryItemModel?, itemView: View?, adapterPosition: Int) {
                    super.onDragStarted(model, itemView, adapterPosition)
                    itemView?.apply {
                        animate()
                            .scaleX(1.05f)
                            .scaleY(1.05f)
                    }
                }

                override fun onDragReleased(model: CategoryItemModel?, itemView: View?) {
                    super.onDragReleased(model, itemView)
                    itemView?.apply {
                        animate()
                            .scaleX(1f)
                            .scaleY(1f)
                    }
                }

                override fun clearView(model: CategoryItemModel?, itemView: View?) {
                    super.clearView(model, itemView)
                    onDragReleased(model, itemView)
                }

                override fun onModelMoved(
                    fromPosition: Int,
                    toPosition: Int,
                    modelBeingMoved: CategoryItemModel?,
                    itemView: View?
                ) {
                    val categoryId = modelBeingMoved!!.id()
                    presenter.onCategoryPositionChanged(categoryId, fromPosition, toPosition)
                }
            })
        rvController.setOnDeleteClickListener { categoryId -> presenter.onDeleteCategoryClick(categoryId) }
        rvController.setOnClickListener { categoryId -> presenter.onCategoryClick(categoryId) }
        categories_rv.adapter = rvController.adapter
        categories_rv.layoutManager = LinearLayoutManager(context)
    }

    override fun showCategories(categories: List<Category>) {
        rvController.setData(categories)
    }

    @ProvidePresenter
    override fun providePresenter(): CategoriesPresenter {
        return super.providePresenter()
    }

}