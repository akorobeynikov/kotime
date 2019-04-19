package ru.softstone.kotime.presentation.about

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
import kotlinx.android.synthetic.main.fragment_about.*
import ru.softstone.kotime.BuildConfig
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseNavigationFragment

class AboutFragment : BaseNavigationFragment<AboutPresenter>(), AboutView {
    companion object {
        fun newInstance() = AboutFragment()
    }

    @InjectPresenter
    lateinit var presenter: AboutPresenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_button.setOnClickListener { presenter.onBackPressed() }
        version_view.text = getString(R.string.version, BuildConfig.VERSION_NAME)
        contact_button.setOnClickListener {
            val address = getString(R.string.support_email)
            val chooserTitle = getString(R.string.email_chooser_title)
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", address, null
                )
            )
            startActivity(Intent.createChooser(emailIntent, chooserTitle))
        }
    }

    @ProvidePresenter
    override fun providePresenter(): AboutPresenter {
        return super.providePresenter()
    }

    override fun getBottomNavigationVisibility(): Boolean {
        return false
    }

}