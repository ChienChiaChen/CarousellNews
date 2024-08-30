package com.chiachen.data.fake.network

import com.chiachen.common.utils.DataResult
import com.chiachen.data.model.NewDataModel
import com.chiachen.data.source.network.NewsNetworkDataSource

class FakeNewsNetworkDataSource(
    private val news: DataResult<List<NewDataModel>>
) : NewsNetworkDataSource {
    override suspend fun getNews(): DataResult<List<NewDataModel>> {
        return news
    }
}