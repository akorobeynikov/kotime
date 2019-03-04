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
import ru.softstone.kotime.presentation.log.model.LogItem
import ru.softstone.kotime.presentation.log.rv.LogsRvController
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
        rvController.setData(
            listOf(
                LogItem("one", "23:00 - 1:34"),
                LogItem("two", "23:00 - 1:34")
            )
        )
    }

    @ProvidePresenter
    override fun providePresenter(): LogPresenter {
        return super.providePresenter()
    }

}