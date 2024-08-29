package com.chiachen.preferences.di

import com.chiachen.data.source.preference.PreferencesDataSource
import com.chiachen.preferences.source.DefaultPreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun providePreferences(
        datasource: DefaultPreferencesDataSource
    ): PreferencesDataSource
}
