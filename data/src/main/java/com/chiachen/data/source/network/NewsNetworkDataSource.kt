package com.chiachen.data.source.network

import com.chiachen.common.utils.DataResult
import com.chiachen.data.model.NewDataModel

interface NewsNetworkDataSource {
    suspend fun getNews(): DataResult<List<NewDataModel>>
}