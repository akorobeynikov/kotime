package ru.softstone.kotime.presentation.actions.edit

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.datetime.timePicker
import com.afollestad.materialdialogs.list.listItems
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_edit_action.*
import kotlinx.android.synthetic.main.part_edit_text_toolbar.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseNavigationFragment
import ru.softstone.kotime.architecture.presentation.hideKeyboard
import ru.softstone.kotime.architecture.presentation.showSoftKeyboard
import ru.softstone.kotime.presentation.getFormattedDuration
import java.util.*


class ActionFragment : BaseNavigationFragment<ActionPresenter>(), ActionView {
    companion object {
        fun newInstance() = ActionFragment()
    }

    @InjectPresenter
    lateinit var presenter: ActionPresenter

    private var startTime: Date? = null
    private var endTime: Date? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start_time_view.setOnClickListener {
            showTimePicker({ presenter.startTimeChanged(it) }, startTime)
        }
        end_time_view.setOnClickListener {
            showTimePicker({ presenter.endTimeChanged(it) }, endTime)
        }
        start_date_view.setOnClickListener {
            showDatePicker({ presenter.startTimeChanged(it) }, startTime)
        }
        end_date_view.setOnClickListener {
            showDatePicker({ presenter.endTimeChanged(it) }, endTime)
        }
        next_button.setOnClickListener {
            hideKeyboard(context!!)
            val description = text_field.text.toString()
            presenter.onAddActionClick(description)
        }
        category_layout.setOnClickListener {
            presenter.onCategoryClick()
        }
        minus_button.setOnClickListener { presenter.onMinusDurationClick() }
        plus_button.setOnClickListener { presenter.onPlusDurationClick() }
        showSoftKeyboard(text_field)
        back_button.setOnClickListener {
            hideKeyboard(context!!)
            presenter.onBackPressed()
        }

    }

    override fun showDuration(seconds: Int, correctionSeconds: Int) {
        val duration = getFormattedDuration(seconds)
        val correction = getFormattedDuration(correctionSeconds, showSign = true)
        duration_view.text = getString(R.string.duration_with_correction, duration, correction)
    }

    private fun showTimePicker(listener: (Date) -> Unit, default: Date?) {
        hideKeyboard(context!!)
        val calendar = Calendar.getInstance().apply { time = default ?: Date() }
        MaterialDialog(context!!).show {
            timePicker(
                currentTime = calendar,
                show24HoursView = DateFormat.is24HourFormat(context)
            ) { _, datetime -> listener.invoke(datetime.time) }
        }
    }

    private fun showDatePicker(listener: (Date) -> Unit, default: Date?) {
        hideKeyboard(context!!)
        val calendar = Calendar.getInstance().apply { time = default ?: Date() }
        MaterialDialog(context!!).show {
            datePicker(currentDate = calendar) { _, datetime ->
                calendar.set(datetime.get(Calendar.YEAR), datetime.get(Calendar.MONTH), datetime.get(Calendar.DATE))
                listener.invoke(calendar.time)
            }
        }
    }

    override fun showStartTime(date: Date) {
        startTime = date
        start_date_view.text = getFormattedDate(date)
        start_time_view.text = getFormattedTime(date)
    }

    override fun showEndTime(date: Date) {
        endTime = date
        end_date_view.text = getFormattedDate(date)
        end_time_view.text = getFormattedTime(date)
    }

    override fun setDescription(description: String) {
        text_field.setText(description)
        text_field.setSelection(text_field.text?.length ?: 0)
    }

    override fun showCategories(categories: List<String>) {
        hideKeyboard(context!!)
        MaterialDialog(context!!).show {
            title(R.string.category)
            listItems(items = categories) { dialog, index, text ->
                presenter.onCategorySelected(index)
            }
        }
    }

    override fun showSelectedCategory(categoryName: String) {
        category_view.text = categoryName
    }

    private fun getFormattedTime(date: Date): String {
        val flags = DateUtils.FORMAT_SHOW_TIME
        return DateUtils.formatDateTime(context, date.time, flags)
    }

    private fun getFormattedDate(date: Date): String {
        val flags = DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY
        return DateUtils.formatDateTime(context, date.time, flags)
    }

    @ProvidePresenter
    override fun providePresenter(): ActionPresenter {
        return super.providePresenter()
    }

    override fun getBottomNavigationVisibility(): Boolean {
        return false
    }
}