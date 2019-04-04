package ru.softstone.kotime.presentation

import kotlin.math.absoluteValue

fun getFormattedDuration(time: Int, showSeconds: Boolean = false, showSign: Boolean = false): String {
    val positiveTime = time.absoluteValue
    val hours = positiveTime / 3600
    val minutes = (positiveTime % 3600) / 60
    val seconds = positiveTime % 60
    val sign = if (time < 0) "-" else "+"
    val formatPrefix = if (showSign) sign else ""
    return if (showSeconds) {
        "$formatPrefix%01d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "$formatPrefix%01d:%02d".format(hours, minutes)
    }
}

