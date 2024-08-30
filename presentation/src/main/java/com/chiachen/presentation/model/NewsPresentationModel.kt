package com.chiachen.presentation.model

data class NewsPresentationModel(
    val id: String,
    val bannerUrl: String,
    val description: String,
    val rank: Int,
    val timeCreated: Long,
    val title: String
)
