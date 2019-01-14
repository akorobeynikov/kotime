package ru.softstone.kotime.presentation.timer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_timer.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment

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
        record_button.setOnClickListener { presenter.onRecordClick() }

        description_field.setAdapter(adapter)
        adapter.setData(listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"))
    }

    override fun showTime(seconds: Int) {
        timer_view.text = getFormattedTime(seconds)
    }

    private fun getFormattedTime(time: Int): String {
        if (time < 0) {
            throw IllegalArgumentException("Time can't be less then zero.")
        }
        val hours = time / 3600
        val minutes = (time % 3600) / 60
        val seconds = time % 60
        val minutesAndSeconds = "%02d:%02d".format(minutes, seconds)
        return if (hours > 0) "$hours:$minutesAndSeconds" else minutesAndSeconds
    }

    @ProvidePresenter
    override fun providePresenter(): TimerPresenter {
        return super.providePresenter()
    }

}