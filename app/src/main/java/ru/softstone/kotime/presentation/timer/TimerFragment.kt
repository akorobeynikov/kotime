package ru.softstone.kotime.presentation.timer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_timer.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment
import ru.softstone.kotime.presentation.getFormattedTime

class TimerFragment : BaseFragment<TimerPresenter>(), TimerView {
    companion object {
        fun newInstance() = TimerFragment()
    }

    private lateinit var adapter: AutoSuggestAdapter

    @InjectPresenter
    lateinit var presenter: TimerPresenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        adapter = AutoSuggestAdapter(context, android.R.layout.simple_dropdown_item_1line)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        show_logs_button.setOnClickListener { presenter.onShowLogsClick() }
        show_categories_button.setOnClickListener { presenter.onShowCategoriesClick() }
        timer_button.setOnClickListener { presenter.onTimerClick() }

//        ArrayAdapter.createFromResource(
//            this,
//            R.array.planets_array,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinner.adapter = adapter
//        }

        description_field.setAdapter(adapter)
//        adapter.setData(listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"))

        record_button.setOnClickListener {
            val description = description_field.text.toString()
            val categoryPosition = category_spinner.selectedItemPosition
            presenter.onRecordClick(description, categoryPosition)
        }
    }

    override fun showCategories(categories: List<String>) {
        context?.let {
            category_spinner.adapter = ArrayAdapter(it, android.R.layout.simple_list_item_1, categories)
        }
    }

    override fun showTime(seconds: Int) {
        timer_view.text = getFormattedTime(seconds)
    }

    @ProvidePresenter
    override fun providePresenter(): TimerPresenter {
        return super.providePresenter()
    }

}