package ru.softstone.kotime.presentation.main

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.AndroidInjection
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.BaseActivity
import ru.softstone.kotime.presentation.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityPresenter>(), ActivityView {
    @InjectPresenter
    lateinit var presenter: ActivityPresenter

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(Navigator(this, R.id.content, supportFragmentManager))
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }


    @ProvidePresenter
    override fun providePresenter(): ActivityPresenter {
        return super.providePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
