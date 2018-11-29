package ru.softstone.kotime.architecture.presentation

import android.app.Activity
import android.support.v4.app.FragmentManager
import android.widget.Toast
import ru.terrakok.cicerone.android.SupportFragmentNavigator

abstract class BaseNavigator(private val activity: Activity, containerId: Int, fragmentManager: FragmentManager) :
    SupportFragmentNavigator(fragmentManager, containerId) {
    override fun exit() {
        activity.finish()
    }

    override fun showSystemMessage(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}