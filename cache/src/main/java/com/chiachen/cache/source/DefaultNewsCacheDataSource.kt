package com.chiachen.cache.source

import com.chiachen.cache.db.news.NewsDao
import com.chiachen.cache.mapper.NewsCacheDataMapper
import com.chiachen.data.model.NewDataModel
import com.chiachen.data.source.cache.NewsCacheDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultNewsCacheDataSource @Inject constructor(
    private val newsDao: NewsDao,
    private val newsCacheDataMapper: NewsCacheDataMapper,
) : NewsCacheDataSource {
    override fun getNews(): Flow<List<NewDataModel>> {
        return newsDao.getNews().map {
            newsCacheDataMapper.fromList(it)
        }
    }

    override suspend fun saveNews(news: List<NewDataModel>) {
        newsCacheDataMapper.toList(news).let {
            newsDao.saveNews(it)
        }
    }
}