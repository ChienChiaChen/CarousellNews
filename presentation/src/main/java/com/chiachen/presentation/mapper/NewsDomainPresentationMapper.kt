package com.chiachen.presentation.mapper

import com.chiachen.common.mapper.Mapper
import com.chiachen.domain.model.NewsDomainModel
import com.chiachen.presentation.model.NewsPresentationModel
import javax.inject.Inject

class NewsDomainPresentationMapper @Inject constructor() :
    Mapper<NewsDomainModel, NewsPresentationModel> {
    override fun from(i: NewsDomainModel): NewsPresentationModel {
        return NewsPresentationModel(
            id = i.id,
            bannerUrl = i.bannerUrl,
            description = i.description,
            rank = i.rank,
            timeCreated = i.timeCreated,
            title = i.title
        )
    }

    override fun to(o: NewsPresentationModel): NewsDomainModel {
        return NewsDomainModel(
            id = o.id,
            bannerUrl = o.bannerUrl,
            description = o.description,
            rank = o.rank,
            timeCreated = o.timeCreated,
            title = o.title,
        )
    }
}