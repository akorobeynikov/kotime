package ru.softstone.kotime.architecture.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import ru.softstone.kotime.BuildConfig
import ru.softstone.kotime.architecture.domain.Logger
import javax.inject.Inject

class AndroidLogger @Inject constructor(private val context: Context) : Logger {
    companion object {
        private const val TAG = "MyLogger"
    }

    override fun debug(msg: String) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(context, "Debug: $msg", Toast.LENGTH_SHORT).show()
        }
    }

    override fun error(msg: String, throwable: Throwable?) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(context, "Error: $msg", Toast.LENGTH_SHORT).show()
        } else {
            Log.e(TAG, msg)
        }
        throwable?.printStackTrace()
    }

    override fun warning(msg: String, throwable: Throwable?) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(context, "Warning: $msg", Toast.LENGTH_SHORT).show()
        } else {
            Log.e(TAG, msg)
        }
        throwable?.printStackTrace()
    }
}