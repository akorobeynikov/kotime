package ru.softstone.kotime.domain.time

interface TimeSource {
    fun getCurrentTime(): Long
}