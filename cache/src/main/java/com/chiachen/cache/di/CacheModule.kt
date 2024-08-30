package com.chiachen.cache.di

import android.content.Context
import androidx.room.Room
import com.chiachen.cache.db.CarousellNewsDatabase
import com.chiachen.cache.db.news.NewsDao
import com.chiachen.cache.source.DefaultNewsCacheDataSource
import com.chiachen.data.source.cache.NewsCacheDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {
    @Binds
    abstract fun provideNewsCacheDataSource(
        datasource: DefaultNewsCacheDataSource
    ): NewsCacheDataSource

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): CarousellNewsDatabase {
            return Room.databaseBuilder(
                context,
                CarousellNewsDatabase::class.java,
                "CarousellNews.db"
            ).build()
        }

        @Provides
        fun provideNewsDao(
            carousellNewsDatabase: CarousellNewsDatabase
        ): NewsDao = carousellNewsDatabase.newsDao()
    }
}