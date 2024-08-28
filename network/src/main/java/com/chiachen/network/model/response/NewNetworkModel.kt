package com.chiachen.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewNetworkModel(
    @field:Json(name ="banner_url")
    val bannerUrl: String?,
    @field:Json(name ="description")
    val description: String?,
    @field:Json(name ="id")
    val id: String?,
    @field:Json(name ="rank")
    val rank: Int?,
    @field:Json(name ="time_created")
    val timeCreated: Long?,
    @field:Json(name ="title")
    val title: String?
)
