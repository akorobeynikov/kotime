package ru.softstone.kotime.presentation.log

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_log.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseFragment
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.presentation.log.model.LogItem
import ru.softstone.kotime.presentation.log.rv.LogsRvController
import java.util.*
import javax.inject.Inject

class LogFragment : BaseFragment<LogPresenter>(), LogView {
    companion object {
        fun newInstance() = LogFragment()
    }

    @InjectPresenter
    lateinit var presenter: LogPresenter

    @Inject
    lateinit var rvController: LogsRvController

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log_rv.adapter = rvController.adapter
        log_rv.layoutManager = LinearLayoutManager(context)
    }

    override fun showActions(actions: List<ActionAndCategory>) {
        rvController.setData(actions.map {
            val timeFormat = android.text.format.DateFormat.getTimeFormat(context)
            val statTime = timeFormat.format(Date(it.startTime))
            val endTime = timeFormat.format(Date(it.endTime))
            LogItem(it.description, it.categoryName, "$statTime - $endTime")
        })
    }

    @ProvidePresenter
    override fun providePresenter(): LogPresenter {
        return super.providePresenter()
    }

}