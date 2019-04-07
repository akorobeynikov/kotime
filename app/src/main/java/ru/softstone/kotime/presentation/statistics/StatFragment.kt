package ru.softstone.kotime.presentation.statistics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_stat.*
import kotlinx.android.synthetic.main.part_date_chooser.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseNavigationFragment
import ru.softstone.kotime.domain.statistics.model.Stats
import ru.softstone.kotime.presentation.statistics.rv.StatsRvController
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

class StatFragment : BaseNavigationFragment<StatPresenter>(), StatView {
    companion object {
        fun newInstance() = StatFragment()
    }

    private lateinit var dateFormat: DateFormat

    @InjectPresenter
    lateinit var presenter: StatPresenter

    @Inject
    lateinit var rvController: StatsRvController

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        dateFormat = android.text.format.DateFormat.getDateFormat(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stats_rv.adapter = rvController.adapter
        stats_rv.layoutManager = LinearLayoutManager(context)
        plus_date_button.setOnClickListener { presenter.onPlusDateClick() }
        minus_date_button.setOnClickListener { presenter.onMinusDateClick() }
    }

    override fun showDate(date: Date) {
        date_view.text = dateFormat.format(date)
    }

    override fun showStats(stats: Stats) {
        rvController.setData(stats)
    }

    @ProvidePresenter
    override fun providePresenter(): StatPresenter {
        return super.providePresenter()
    }
}