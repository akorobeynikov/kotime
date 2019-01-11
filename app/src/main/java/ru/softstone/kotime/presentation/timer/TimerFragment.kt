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
        description_field.setAdapter(adapter)
        adapter.setData(listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"))
    }

    @ProvidePresenter
    override fun providePresenter(): TimerPresenter {
        return super.providePresenter()
    }

}