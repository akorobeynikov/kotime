package ru.softstone.kotime.domain.error.model

data class KotimeError(val description: String, val throwable: Throwable)