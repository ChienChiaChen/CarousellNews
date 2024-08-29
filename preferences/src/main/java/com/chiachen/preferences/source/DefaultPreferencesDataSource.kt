package com.chiachen.preferences.source

import android.content.Context
import androidx.core.content.edit
import com.chiachen.data.source.preference.PreferencesDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPreferencesDataSource @Inject constructor(
    @ApplicationContext context: Context,
) : PreferencesDataSource {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    override fun needToRefresh():Boolean {
        val oldTime = preferences.getLong(TIMESTAMP, 0L)
        val currTime = System.currentTimeMillis()/1000
        return if ((currTime - oldTime) > ONE_HOUR) {
            preferences.edit {
                putLong(TIMESTAMP, currTime)
                apply()
            }
            true
        } else {
            false
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "CAROUSELL_NEWS_PREFERENCES"
        private const val TIMESTAMP = "TIMESTAMPE"
        private const val ONE_HOUR = 1000 * 60 * 60
    }


}