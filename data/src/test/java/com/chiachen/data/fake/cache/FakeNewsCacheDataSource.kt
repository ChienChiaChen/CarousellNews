package com.chiachen.data.fake.cache


import com.chiachen.data.fake.FakeDataGenerator.news
import com.chiachen.data.model.NewDataModel
import com.chiachen.data.source.cache.NewsCacheDataSource

class FakeNewsCacheDataSource : NewsCacheDataSource {
    override fun getNews(): List<NewDataModel> {
        return news
    }

    override suspend fun saveNews(news: List<NewDataModel>) {

    }
}