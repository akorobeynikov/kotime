package ru.softstone.kotime.presentation.error

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseNavigationFragment

class ErrorFragment : BaseNavigationFragment<ErrorPresenter>(), ErrorView {
    companion object {
        fun newInstance() = ErrorFragment()
    }

    @InjectPresenter
    lateinit var presenter: ErrorPresenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    @ProvidePresenter
    override fun providePresenter(): ErrorPresenter {
        return super.providePresenter()
    }

    override fun getBottomNavigationVisibility(): Boolean {
        return false
    }

}