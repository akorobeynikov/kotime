package ru.softstone.kotime.presentation.category.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyTouchHelper
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_categories.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseNavigationFragment
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.presentation.category.list.rv.CategoriesRvController
import ru.softstone.kotime.presentation.category.list.rv.CategoryItemModel
import javax.inject.Inject

class CategoriesFragment : BaseNavigationFragment<CategoriesPresenter>(),
    CategoriesView {
    companion object {
        fun newInstance() = CategoriesFragment()
    }

    @InjectPresenter
    lateinit var presenter: CategoriesPresenter

    @Inject
    lateinit var rvController: CategoriesRvController

    private var swipeEnabled = false

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

        initDragging()
        initSwiping()
        rvController.setOnClickListener { categoryId -> presenter.onCategoryClick(categoryId) }
        categories_rv.adapter = rvController.adapter
        val layoutManager = LinearLayoutManager(context)
        categories_rv.layoutManager = layoutManager
        categories_rv.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
    }

    private fun initSwiping() {
        EpoxyTouchHelper.initSwiping(categories_rv)
            .leftAndRight()
            .withTarget(CategoryItemModel::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<CategoryItemModel>() {
                override fun isSwipeEnabledForModel(model: CategoryItemModel?): Boolean {
                    return super.isSwipeEnabledForModel(model) and swipeEnabled
                }

                override fun onSwipeCompleted(
                    model: CategoryItemModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    val categoryId = model?.id()?.toInt()
                    if (categoryId != null) {
                        presenter.onDeleteCategoryClick(categoryId)
                    }
                }
            })

    }

    private fun initDragging() {
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
    }

    override fun showCategories(categories: List<Category>) {
        swipeEnabled =
            categories.count() > 1 // запрещаем удалять свайпом, если осталась одна категория //todo это бизнес логика. лучше вынести из фрагмента
        rvController.setData(categories)
    }

    @ProvidePresenter
    override fun providePresenter(): CategoriesPresenter {
        return super.providePresenter()
    }

}