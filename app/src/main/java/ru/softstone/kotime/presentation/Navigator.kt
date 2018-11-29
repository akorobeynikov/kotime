package ru.softstone.kotime.presentation

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import ru.softstone.kotime.architecture.presentation.BaseNavigator
import ru.softstone.kotime.presentation.splash.SplashFragment

class Navigator(activity: Activity, containerId: Int, fragmentManager: FragmentManager) :
    BaseNavigator(activity, containerId, fragmentManager) {

    override fun createFragment(screenKey: String?, data: Any?): Fragment = when (screenKey) {
        SPLASH_SCREEN -> SplashFragment.newInstance()
        else -> throw IllegalStateException("Unknown screenKey: $screenKey")
    }
}