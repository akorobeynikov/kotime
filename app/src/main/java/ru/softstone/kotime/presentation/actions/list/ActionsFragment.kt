package ru.softstone.kotime.presentation.actions.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_actions.*
import kotlinx.android.synthetic.main.part_date_chooser.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseNavigationFragment
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.presentation.actions.list.model.ActionItem
import ru.softstone.kotime.presentation.actions.list.rv.ActionsRvController
import ru.softstone.kotime.presentation.getFormattedDuration
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ActionsFragment : BaseNavigationFragment<ActionsPresenter>(),
    ActionsView {
    companion object {
        fun newInstance() = ActionsFragment()
    }

    private lateinit var dateFormat: DateFormat

    @InjectPresenter
    lateinit var presenter: ActionsPresenter

    @Inject
    lateinit var rvController: ActionsRvController

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        dateFormat = android.text.format.DateFormat.getDateFormat(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actions_rv.adapter = rvController.adapter
        actions_rv.layoutManager = LinearLayoutManager(context)
        plus_date_button.setOnClickListener { presenter.onPlusDateClick() }
        minus_date_button.setOnClickListener { presenter.onMinusDateClick() }
        add_record_button.setOnClickListener { presenter.onAddAction() }
        rvController.setDeleteListener { presenter.onDeleteAction(it) }
        rvController.setItemClickListener { presenter.onEditAction(it) }
        //todo удалить
//        (activity as AppCompatActivity).apply {
//            setSupportActionBar(toolbar_view)
////            supportActionBar?.setDisplayHomeAsUpEnabled(true)
////            supportActionBar?.title = "test"
//        }
    }

    override fun showDate(date: Date) {
        date_view.text = dateFormat.format(date)
    }

    override fun showActions(actions: List<ActionAndCategory>) {
        rvController.setData(actions.map {
            val timeFormat = android.text.format.DateFormat.getTimeFormat(context)
            val statTime = timeFormat.format(Date(it.startTime))
            val endTime = timeFormat.format(Date(it.endTime))
            val durationSeconds = TimeUnit.MILLISECONDS.toSeconds(it.endTime - it.startTime).toInt()
            val duration = getFormattedDuration(durationSeconds)
            ActionItem(
                it.uid,
                it.description,
                it.categoryName,
                "$duration ($statTime - $endTime)"
            )
        })
    }

    @ProvidePresenter
    override fun providePresenter(): ActionsPresenter {
        return super.providePresenter()
    }

}