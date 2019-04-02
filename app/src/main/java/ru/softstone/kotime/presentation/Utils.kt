package ru.softstone.kotime.presentation

fun getFormattedDuration(time: Int): String {
    if (time < 0) {
        throw IllegalArgumentException("Time can't be less then zero.")
    }
    val hours = time / 3600
    val minutes = (time % 3600) / 60
    val seconds = time % 60
    val minutesAndSeconds = "%02d:%02d".format(minutes, seconds)
    return if (hours > 0) "$hours:$minutesAndSeconds" else minutesAndSeconds
}

