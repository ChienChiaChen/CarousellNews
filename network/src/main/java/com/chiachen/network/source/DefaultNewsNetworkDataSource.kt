package com.chiachen.network.source

import com.chiachen.common.utils.DataResult
import com.chiachen.data.model.NewDataModel
import com.chiachen.data.source.network.NewsNetworkDataSource
import com.chiachen.network.apiservice.NewsApiService
import com.chiachen.network.mapper.NewsNetworkDataMapper
import javax.inject.Inject

class DefaultNewsNetworkDataSource@Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsNetworkDataMapper: NewsNetworkDataMapper,
) : NewsNetworkDataSource {
    override suspend fun getNews(): DataResult<List<NewDataModel>> {
        return try {
            val news = newsApiService.getNews()
            DataResult.Success(newsNetworkDataMapper.fromList(news))
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}