package com.chiachen.data.mapper

import com.chiachen.common.mapper.Mapper
import com.chiachen.data.model.NewDataModel
import com.chiachen.domain.model.NewsDomainModel
import javax.inject.Inject

class NewsDataDomainMapper @Inject constructor() : Mapper<NewDataModel, NewsDomainModel> {
    override fun from(i: NewDataModel): NewsDomainModel {
        return NewsDomainModel(
            id = i.id,
            bannerUrl = i.bannerUrl,
            description = i.description,
            rank = i.rank,
            timeCreated = i.timeCreated,
            title = i.title
        )
    }

    override fun to(o: NewsDomainModel): NewDataModel {
        return NewDataModel(
            id = o.id,
            bannerUrl = o.bannerUrl,
            description = o.description,
            rank = o.rank,
            timeCreated = o.timeCreated,
            title = o.title,
        )
    }
}