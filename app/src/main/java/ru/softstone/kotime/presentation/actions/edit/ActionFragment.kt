package ru.softstone.kotime.presentation.actions.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_edit_action.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment
import ru.softstone.kotime.presentation.getFormattedDuration
import java.text.DateFormat
import java.util.*


class ActionFragment : BaseFragment<ActionPresenter>(), ActionView {
    companion object {
        fun newInstance() = ActionFragment()
    }

    @InjectPresenter
    lateinit var presenter: ActionPresenter

    private lateinit var dateFormat: DateFormat

    private lateinit var timeFormat: DateFormat
    private var startTime: Date? = null
    private var endTime: Date? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        dateFormat = android.text.format.DateFormat.getMediumDateFormat(context)
        timeFormat = android.text.format.DateFormat.getTimeFormat(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_start_time_button.setOnClickListener {
            showTimePicker({ presenter.startTimeChanged(it) }, getString(R.string.start_time_picker_title), startTime)
        }
        edit_end_time_button.setOnClickListener {
            showTimePicker({ presenter.endTimeChanged(it) }, getString(R.string.end_time_picker_title), endTime)
        }
        add_record_button.setOnClickListener {
            val description = description_field.text.toString()
            val categoryIndex = categories_spinner.selectedIndex
            presenter.onAddActionClick(description, categoryIndex)
        }
        minus_button.setOnClickListener { presenter.onMinusDurationClick() }
        plus_button.setOnClickListener { presenter.onPlusDurationClick() }
    }

    override fun showDuration(seconds: Int, correctionSeconds: Int) {
        val duration = getFormattedDuration(seconds)
        val correction = getFormattedDuration(correctionSeconds, showSign = true)
        duration_view.text = getString(R.string.duration_with_correction, duration, correction)
    }

    private fun showTimePicker(listener: (Date) -> Unit, title: String, default: Date?) {
        SingleDateAndTimePickerDialog.Builder(context)
            .apply {
                default?.let { defaultDate(it) }
            }
            .minutesStep(1)
            .title(title)
            .listener(listener)
            .display()
    }

    override fun showStartTime(date: Date) {
        startTime = date
        start_time_view.text = getFormattedTime(date)
    }

    override fun showEndTime(date: Date) {
        endTime = date
        end_time_view.text = getFormattedTime(date)
    }

    override fun setDescription(description: String) {
        description_field.setText(description)
    }

    override fun showCategories(categories: List<String>) {
        categories_spinner.attachDataSource(categories)
    }

    override fun setSelectedCategory(index: Int) {
        categories_spinner.selectedIndex = index
    }

    private fun getFormattedTime(date: Date): String {
        return dateFormat.format(date) + " " + timeFormat.format(date)
    }

    @ProvidePresenter
    override fun providePresenter(): ActionPresenter {
        return super.providePresenter()
    }

}