package com.chiachen.cache.mapper

import com.chiachen.cache.model.NewsCacheModel
import com.chiachen.common.mapper.Mapper
import com.chiachen.data.model.NewDataModel
import javax.inject.Inject

class NewsCacheDataMapper @Inject constructor() : Mapper<NewsCacheModel, NewDataModel> {
    override fun from(i: NewsCacheModel): NewDataModel {
        return NewDataModel(
            id = i.id,
            bannerUrl = i.bannerUrl,
            description = i.description,
            rank = i.rank,
            timeCreated = i.timeCreated,
            title = i.title
        )
    }

    override fun to(o: NewDataModel): NewsCacheModel {
        return NewsCacheModel(
            id = o.id,
            bannerUrl = o.bannerUrl,
            description = o.description,
            rank = o.rank,
            timeCreated = o.timeCreated,
            title = o.title,
        )
    }
}