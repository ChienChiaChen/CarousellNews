package com.chiachen.cache.db.news

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chiachen.cache.model.NewsCacheModel

interface NewsDao {
    @Query(
        """
        SELECT * FROM news 
        """
    )
    fun getNews(): List<NewsCacheModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNews(newsCacheModels: List<NewsCacheModel>)
}