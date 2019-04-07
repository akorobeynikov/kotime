package ru.softstone.kotime.presentation

import android.content.Context
import android.text.format.DateUtils
import java.util.*
import kotlin.math.absoluteValue

fun getFormattedDuration(time: Int, showSeconds: Boolean = false, showSign: Boolean = false): String {
    val positiveTime = time.absoluteValue
    val hours = positiveTime / 3600
    val minutes = (positiveTime % 3600) / 60
    val seconds = positiveTime % 60
    val sign = if (time < 0) "-" else "+"
    val formatPrefix = if (showSign) sign else ""
    return if (showSeconds) {
        "$formatPrefix%02d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "$formatPrefix%02d:%02d".format(hours, minutes)
    }
}

fun getFormattedDate(context: Context, date: Date): String {
    val flags =
        DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NO_YEAR or DateUtils.FORMAT_ABBREV_WEEKDAY or DateUtils.FORMAT_SHOW_WEEKDAY
    return DateUtils.formatDateTime(context, date.time, flags)
}
