package ru.softstone.kotime.presentation.actions.edit.behavior

import java.util.*

interface ActionBehavior {
    fun start()
    fun onAddActionClick(description: String)
    fun endTimeChanged(date: Date)
    fun startTimeChanged(date: Date)
    fun onDestroy()
    fun onMinusDurationClick()
    fun onPlusDurationClick()
    fun onCategoryClick()
    fun onCategorySelected(index: Int)
}