package com.chiachen.data.fake.preferences

import com.chiachen.data.source.preference.PreferencesDataSource
import javax.inject.Inject

class FakePreferences @Inject constructor() : PreferencesDataSource {
    private val preferences = mutableMapOf<String, Any>()

    override fun updateTimestamp() {
        preferences["TIMESTAMPE"] = 0L
    }

    override fun getTimestamp(): Long {
        return preferences["TIMESTAMPE"] as Long
    }
}