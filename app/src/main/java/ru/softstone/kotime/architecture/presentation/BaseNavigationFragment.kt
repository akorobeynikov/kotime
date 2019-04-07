package ru.softstone.kotime.architecture.presentation

import ru.softstone.kotime.presentation.main.BottomNavigationActivity

abstract class BaseNavigationFragment<Presenter : BasePresenter<*>> : BaseFragment<Presenter>() {
    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity is BottomNavigationActivity) {
            activity.showBottomNavigation(getBottomNavigationVisibility())
        }
    }

    open fun getBottomNavigationVisibility() = true
}