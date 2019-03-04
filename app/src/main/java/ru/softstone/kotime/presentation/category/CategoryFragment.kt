package ru.softstone.kotime.presentation.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_category.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.presentation.category.rv.CategoriesRvController
import javax.inject.Inject

class CategoryFragment : BaseFragment<CategoryPresenter>(), CategoryView {
    companion object {
        fun newInstance() = CategoryFragment()
    }

    @InjectPresenter
    lateinit var presenter: CategoryPresenter

    @Inject
    lateinit var rvController: CategoriesRvController

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_category_button.setOnClickListener {
            val categoryName = category_field.text.toString()
            presenter.onAddCategoryClick(categoryName)
        }

        rvController.setOnDeleteClickListener { categoryId -> presenter.onDeleteCategoryClick(categoryId) }
        categories_rv.adapter = rvController.adapter
        categories_rv.layoutManager = LinearLayoutManager(context)
    }

    override fun showCategories(categories: List<Category>) {
        rvController.setData(categories)
    }

    @ProvidePresenter
    override fun providePresenter(): CategoryPresenter {
        return super.providePresenter()
    }

}