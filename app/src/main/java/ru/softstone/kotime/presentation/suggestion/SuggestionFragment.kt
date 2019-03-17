package ru.softstone.kotime.presentation.suggestion

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_suggestion.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment
import ru.softstone.kotime.presentation.suggestion.model.Suggestion
import ru.softstone.kotime.presentation.suggestion.rv.SuggestionsRvController
import javax.inject.Inject

class SuggestionFragment : BaseFragment<SuggestionPresenter>(), SuggestionView {
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
        rvController.setOnClickListener { categoryId, description ->
            presenter.onSuggestionClick(
                categoryId,
                description
            )
        }
        suggestions_rv.adapter = rvController.adapter
        suggestions_rv.layoutManager = LinearLayoutManager(context)
    }

    override fun showSuggestions(suggestions: List<Suggestion>) {
        rvController.setData(suggestions)
    }

    @ProvidePresenter
    override fun providePresenter(): SuggestionPresenter {
        return super.providePresenter()
    }

}