package ru.softstone.kotime.presentation.actions.edit.behavior

import java.util.*

interface ActionBehavior {
    fun start()
    fun onAddActionClick(description: String, categoryIndex: Int)
    fun endTimeChanged(date: Date)
    fun startTimeChanged(date: Date)
    fun onDestroy()
    fun onMinusDurationClick()
    fun onPlusDurationClick()
}