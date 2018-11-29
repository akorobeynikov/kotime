package ru.softstone.kotime.architecture.domain

interface Logger {
    fun debug(msg: String)
    fun error(msg: String, throwable: Throwable? = null)
    fun warning(msg: String, throwable: Throwable? = null)
}