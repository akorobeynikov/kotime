package ru.softstone.kotime.domain.common

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface StringProvider {
    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String
    fun getString(resId: Int, vararg formatArgs: Any): String
    fun getString(@StringRes resId: Int): String
}
