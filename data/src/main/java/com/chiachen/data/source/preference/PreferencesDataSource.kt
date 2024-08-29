package com.chiachen.data.source.preference

interface PreferencesDataSource {

    fun updateTimestamp()

    fun getTimestamp(): Long
}