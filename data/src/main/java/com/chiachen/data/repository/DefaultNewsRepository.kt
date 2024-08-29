package com.chiachen.data.repository

import com.chiachen.common.utils.DataResult
import com.chiachen.data.mapper.NewsDataDomainMapper
import com.chiachen.data.source.cache.NewsCacheDataSource
import com.chiachen.data.source.network.NewsNetworkDataSource
import com.chiachen.data.source.preference.PreferencesDataSource
import com.chiachen.domain.model.NewsDomainModel
import com.chiachen.domain.repository.NewsRepository
import javax.inject.Inject

class DefaultNewsRepository @Inject constructor(
    private val newsCacheDataSource: NewsCacheDataSource,
    private val newsNetworkDataSource: NewsNetworkDataSource,
    private val preferencesDataSource: PreferencesDataSource,
    private val mapper: NewsDataDomainMapper
) : NewsRepository {

    override suspend fun requestNews(): DataResult<List<NewsDomainModel>> {
        return when (val result = newsNetworkDataSource.getNews()) {
            is DataResult.Success -> {
                DataResult.Success(mapper.fromList(result.data))
            }

            is DataResult.Error -> {
                DataResult.Error(result.exception)
            }
        }
    }

    override fun getNews(): List<NewsDomainModel> {
        return newsCacheDataSource.getNews().map { mapper.from(it) }
    }

    override suspend fun saveNews(list: List<NewsDomainModel>) {
        mapper.toList(list).let {
            newsCacheDataSource.saveNews(it)
        }
    }

    override fun needToRefresh(): Boolean {
        val oldTime = preferencesDataSource.getTimestamp()
        val currTime = System.currentTimeMillis() / 1000
        return if ((currTime - oldTime) > ONE_HOUR) {
            preferencesDataSource.updateTimestamp()
            true
        } else {
            false
        }
    }

    companion object {
        private const val ONE_HOUR = 1000 * 60 * 60
    }
}