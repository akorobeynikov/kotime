package ru.softstone.kotime.presentation.contribute

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_contribute.*
import ru.softstone.kotime.BuildConfig
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseNavigationFragment
import java.lang.ref.WeakReference


class ContributeFragment : BaseNavigationFragment<ContributePresenter>(), ContributeView {
    companion object {
        fun newInstance() = ContributeFragment()
    }

    @InjectPresenter
    lateinit var presenter: ContributePresenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contribute, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_button.setOnClickListener { presenter.onBackPressed() }

        donate1_button.setOnClickListener { presenter.onDonateClick(0, WeakReference(activity as Activity)) }
        donate2_button.setOnClickListener { presenter.onDonateClick(1, WeakReference(activity as Activity)) }
        donate3_button.setOnClickListener { presenter.onDonateClick(2, WeakReference(activity as Activity)) }
        rate_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
            startActivity(intent)
        }
    }

    override fun showPrices(price1: String, price2: String, price3: String) {
        donate1_button.visibility = View.VISIBLE
        donate2_button.visibility = View.VISIBLE
        donate3_button.visibility = View.VISIBLE
        donate1_button.text = price1
        donate2_button.text = price2
        donate3_button.text = price3
    }

    @ProvidePresenter
    override fun providePresenter(): ContributePresenter {
        return super.providePresenter()
    }

    override fun getBottomNavigationVisibility(): Boolean {
        return false
    }
}