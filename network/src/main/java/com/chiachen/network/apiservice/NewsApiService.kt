package com.chiachen.network.apiservice

import com.chiachen.network.model.response.NewNetworkModel
import com.chiachen.network.utils.NetworkConstants
import retrofit2.http.GET

interface NewsApiService {

    @GET(NetworkConstants.NEWS_ENDPOINT)
    suspend fun getNews(
    ):List<NewNetworkModel>

}