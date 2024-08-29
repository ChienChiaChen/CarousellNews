package com.chiachen.domain.usecase

import com.chiachen.common.qualifier.IoDispatcher
import com.chiachen.common.utils.DataResult
import com.chiachen.domain.model.NewsDomainModel
import com.chiachen.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): DataResult<List<NewsDomainModel>> {
        // if data is expired
        //      fetch data from remote server
        //      save to db and fetch data from db to make sure consistency

        // if data is not expired
        //      check if we have cache
        //          if cache is null
        //              fetch data from remote server
        //              save to db and fetch data from db to make sure consistency
        //          if cache is not null
        //              return data from cache

        return withContext(dispatcher) {
            if (newsRepository.needToRefresh()) {
                fetchResult()
            } else {
                val news = newsRepository.getNews()
                if (news.isEmpty()) {
                    fetchResult()
                } else {
                    DataResult.Success(newsRepository.getNews())
                }
            }
        }
    }

    private suspend fun fetchResult() =
        when (val result = newsRepository.requestNews()) {
            is DataResult.Success -> {
                newsRepository.saveNews(result.data)
                DataResult.Success(newsRepository.getNews())
            }

            is DataResult.Error -> DataResult.Error(result.exception)
        }
}