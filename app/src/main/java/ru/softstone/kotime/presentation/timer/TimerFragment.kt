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
import ru.softstone.kotime.presentation.getFormattedDuration

class TimerFragment : BaseFragment<TimerPresenter>(), TimerView {
    companion object {
        fun newInstance() = TimerFragment()
    }

    @InjectPresenter
    lateinit var presenter: TimerPresenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        show_logs_button.setOnClickListener { presenter.onShowLogsClick() }
        show_categories_button.setOnClickListener { presenter.onShowCategoriesClick() }
        timer_button.setOnClickListener { presenter.onTimerClick() }
        add_record_button.setOnClickListener { presenter.onAddRecordClick() }
        show_stats_button.setOnClickListener { presenter.onShowStatsClick() }
    }

    override fun showTime(seconds: Int) {
        timer_view.text = getFormattedDuration(seconds)
    }

    @ProvidePresenter
    override fun providePresenter(): TimerPresenter {
        return super.providePresenter()
    }

}