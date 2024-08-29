package com.chiachen.cache.db.news

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chiachen.cache.model.NewsCacheModel
import kotlinx.coroutines.flow.Flow

interface NewsDao {
    @Query(
        """
        SELECT * FROM news 
        """
    )
    fun getNews(): Flow<List<NewsCacheModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNews(newsCacheModels: List<NewsCacheModel>)
}