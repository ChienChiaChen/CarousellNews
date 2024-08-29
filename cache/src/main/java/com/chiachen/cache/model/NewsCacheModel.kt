package com.chiachen.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsCacheModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val bannerUrl: String,
    val description: String,
    val rank: Int,
    val timeCreated: Long,
    val title: String
)
