package com.chiachen.data.source.cache

import com.chiachen.data.model.NewDataModel

interface NewsCacheDataSource {
    fun getNews(): List<NewDataModel>
    suspend fun saveNews(news: List<NewDataModel>)
}