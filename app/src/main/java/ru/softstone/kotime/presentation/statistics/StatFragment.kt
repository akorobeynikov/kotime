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
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment
import ru.softstone.kotime.domain.statistics.model.StatItem
import ru.softstone.kotime.presentation.statistics.rv.StatsRvController
import javax.inject.Inject

class StatFragment : BaseFragment<StatPresenter>(), StatView {
    companion object {
        fun newInstance() = StatFragment()
    }

    @InjectPresenter
    lateinit var presenter: StatPresenter

    @Inject
    lateinit var rvController: StatsRvController

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stats_rv.adapter = rvController.adapter
        stats_rv.layoutManager = LinearLayoutManager(context)
    }

    override fun showStats(stats: List<StatItem>) {
        rvController.setData(stats)
    }

    @ProvidePresenter
    override fun providePresenter(): StatPresenter {
        return super.providePresenter()
    }
}