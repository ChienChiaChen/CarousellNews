package com.chiachen.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewNetworkModel(
    @Json(name ="banner_url")
    val bannerUrl: String?,
    @Json(name ="description")
    val description: String?,
    @Json(name ="id")
    val id: String?,
    @Json(name ="rank")
    val rank: Int?,
    @Json(name ="time_created")
    val timeCreated: Long?,
    @Json(name ="title")
    val title: String?
)
