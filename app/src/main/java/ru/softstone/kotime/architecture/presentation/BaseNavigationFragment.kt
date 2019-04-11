package ru.softstone.kotime.architecture.presentation

import ru.softstone.kotime.presentation.main.BottomNavigationView

abstract class BaseNavigationFragment<Presenter : BasePresenter<*>> : BaseFragment<Presenter>() {
    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity is BottomNavigationView) {
            activity.showBottomNavigation(getBottomNavigationVisibility())
        }
    }

    open fun getBottomNavigationVisibility() = true
}