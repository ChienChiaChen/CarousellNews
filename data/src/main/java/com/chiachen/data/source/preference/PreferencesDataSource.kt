package com.chiachen.data.source.preference

interface PreferencesDataSource {

    fun needToRefresh(): Boolean
}