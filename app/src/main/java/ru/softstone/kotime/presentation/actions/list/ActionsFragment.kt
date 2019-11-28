package ru.softstone.kotime.presentation.actions.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.airbnb.epoxy.EpoxyTouchHelper
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_actions.*
import kotlinx.android.synthetic.main.fragment_actions.chart_view
import kotlinx.android.synthetic.main.fragment_timer.*
import kotlinx.android.synthetic.main.part_date_chooser.*
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseNavigationFragment
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.presentation.actions.list.model.ActionItem
import ru.softstone.kotime.presentation.actions.list.rv.ActionItemModel
import ru.softstone.kotime.presentation.actions.list.rv.ActionsRvController
import ru.softstone.kotime.presentation.customview.chart.ChartItem
import ru.softstone.kotime.presentation.getFormattedDate
import ru.softstone.kotime.presentation.getFormattedDuration
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ActionsFragment : BaseNavigationFragment<ActionsPresenter>(),
    ActionsView {
    companion object {
        fun newInstance() = ActionsFragment()
    }

    @InjectPresenter
    lateinit var presenter: ActionsPresenter

    @Inject
    lateinit var rvController: ActionsRvController

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actions_rv.adapter = rvController.adapter
        val layoutManager = LinearLayoutManager(context)
        actions_rv.layoutManager = layoutManager
        actions_rv.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        plus_date_button.setOnClickListener { presenter.onPlusDateClick() }
        minus_date_button.setOnClickListener { presenter.onMinusDateClick() }
        next_button.setOnClickListener { presenter.onAddAction() }
        calendar_button.setOnClickListener { presenter.onCalendarClick() }
        initSwiping()
        rvController.setItemClickListener { presenter.onEditAction(it) }
    }

    override fun showDateDialog(date: Date) {
        val calendar = Calendar.getInstance().apply { time = date }
        MaterialDialog(context!!).show {
            datePicker(currentDate = calendar) { _, datetime ->
                presenter.onDateSelected(datetime.time)
            }
        }
    }

    override fun showDate(date: Date) {
        date_view.text = getFormattedDate(context!!, date)
    }

    override fun showChart(chatItems: List<ChartItem>) {
        chart_view.showChartItems(chatItems)
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
                "$duration ($statTime - $endTime)",
                it.color
            )
        })
    }

    private fun initSwiping() {
        EpoxyTouchHelper.initSwiping(actions_rv)
            .leftAndRight()
            .withTarget(ActionItemModel::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<ActionItemModel>() {
                override fun onSwipeCompleted(
                    model: ActionItemModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    val actionId = model?.id()?.toInt()
                    if (actionId != null) {
                        presenter.onDeleteAction(actionId)
                    }
                }
            })

    }

    @ProvidePresenter
    override fun providePresenter(): ActionsPresenter {
        return super.providePresenter()
    }

}