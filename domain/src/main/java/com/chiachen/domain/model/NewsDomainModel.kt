package com.chiachen.domain.model

data class NewsDomainModel(
    val id: String,
    val bannerUrl: String,
    val description: String,
    val rank: Int,
    val timeCreated: Long,
    val title: String
)
