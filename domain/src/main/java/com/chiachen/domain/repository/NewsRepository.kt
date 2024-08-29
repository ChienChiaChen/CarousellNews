package com.chiachen.domain.repository

import com.chiachen.common.utils.DataResult
import com.chiachen.domain.model.NewsDomainModel

interface NewsRepository {

    suspend fun requestNews(): DataResult<List<NewsDomainModel>>

    suspend fun saveNews(list: List<NewsDomainModel>)

    fun getNews(): List<NewsDomainModel>

    fun needToRefresh(): Boolean
}