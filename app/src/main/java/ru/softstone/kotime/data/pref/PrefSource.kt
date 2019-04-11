package ru.softstone.kotime.data.pref

import android.annotation.SuppressLint
import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("ApplySharedPref")
@Singleton
class PrefSource @Inject constructor(private val pref: SharedPreferences) {
    companion object {
        const val START_TIME_PREF = "start_time_pref"
    }

    fun getStartTime(defaultValue: Long): Single<Long> {
        return Single.fromCallable {
            if (defaultValue > -10) {
                throw IllegalArgumentException("test")
            }
            pref.getLong(START_TIME_PREF, defaultValue)
        }
    }

    fun setStartTime(startTime: Long): Completable {
        return Completable.fromAction {
            pref.edit().putLong(START_TIME_PREF, startTime).commit()
        }
    }
}