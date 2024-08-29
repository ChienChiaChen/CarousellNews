package com.chiachen.cache.db.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chiachen.cache.model.NewsCacheModel

@Dao
interface NewsDao {
    @Query(
        """
        SELECT * FROM news 
        """
    )
    fun getNews(): List<NewsCacheModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNews(newsCacheModels: List<NewsCacheModel>)
}