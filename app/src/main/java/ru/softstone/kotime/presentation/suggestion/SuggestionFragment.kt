package ru.softstone.kotime.presentation.suggestion

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_suggestion.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseNavigationFragment
import ru.softstone.kotime.architecture.presentation.hideKeyboard
import ru.softstone.kotime.architecture.presentation.showKeyboard
import ru.softstone.kotime.presentation.suggestion.model.Suggestion
import ru.softstone.kotime.presentation.suggestion.rv.SuggestionsRvController
import javax.inject.Inject


class SuggestionFragment : BaseNavigationFragment<SuggestionPresenter>(), SuggestionView {
    companion object {
        fun newInstance() = SuggestionFragment()
    }

    @Inject
    lateinit var rvController: SuggestionsRvController

    @InjectPresenter
    lateinit var presenter: SuggestionPresenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_suggestion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_button.setOnClickListener { presenter.navigateBack() }
        description_field.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.onDescriptionChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }
        })
        rvController.setOnClickListener { _, description ->
            presenter.onSuggestionClick(description)
        }
        rvController.setFastRecordListener { categoryId, description ->
            presenter.onFastRecordClick(categoryId, description)
        }
        rvController.setEditListener { categoryId, description ->
            presenter.onEditClick(categoryId, description)
        }
        suggestions_rv.adapter = rvController.adapter
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            context,
            layoutManager.getOrientation()
        )
        suggestions_rv.layoutManager = layoutManager
        suggestions_rv.addItemDecoration(dividerItemDecoration)
    }

    override fun onResume() {
        super.onResume()
        description_field.requestFocus()
        showKeyboard(context!!)
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(context!!)
    }

    override fun showSuggestions(suggestions: List<Suggestion>) {
        rvController.setData(suggestions)
    }

    override fun setSuggestionDescription(description: String) {
        description_field.setText(description)
        description_field.setSelection(description_field.text?.length ?: 0)
    }

    override fun getBottomNavigationVisibility() = false

    @ProvidePresenter
    override fun providePresenter(): SuggestionPresenter {
        return super.providePresenter()
    }

}