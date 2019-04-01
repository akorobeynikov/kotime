package ru.softstone.kotime.presentation.category.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_category.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment

class CategoryFragment : BaseFragment<CategoryPresenter>(), CategoryView {
    companion object {
        fun newInstance() = CategoryFragment()
    }

    @InjectPresenter
    lateinit var presenter: CategoryPresenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next_button.setOnClickListener {
            val categoryName = category_field.text.toString()
            presenter.onDoneClick(categoryName)
        }
    }

    @ProvidePresenter
    override fun providePresenter(): CategoryPresenter {
        return super.providePresenter()
    }

}