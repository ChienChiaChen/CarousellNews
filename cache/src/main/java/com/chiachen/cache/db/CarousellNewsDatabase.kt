package com.chiachen.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chiachen.cache.db.news.NewsDao
import com.chiachen.cache.model.NewsCacheModel


@Database(
    entities = [
        NewsCacheModel::class
    ],
    version = 1
)
abstract class CarousellNewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}