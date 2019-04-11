package ru.softstone.kotime.data.string

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import ru.softstone.kotime.domain.common.StringProvider
import javax.inject.Inject

class StringProviderImpl @Inject constructor(private val context: Context) : StringProvider {
    override fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    override fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.getResources().getQuantityString(id, quantity, *formatArgs)
    }
}
