package com.chiachen.data.source.cache

import com.chiachen.data.model.NewDataModel
import kotlinx.coroutines.flow.Flow

interface NewsCacheDataSource {
    fun getNews(): Flow<List<NewDataModel>>
    suspend fun saveNews(news: List<NewDataModel>)
}