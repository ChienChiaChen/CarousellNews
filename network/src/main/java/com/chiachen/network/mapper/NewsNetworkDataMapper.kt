package com.chiachen.network.mapper

import com.chiachen.common.mapper.Mapper
import com.chiachen.data.model.NewDataModel
import com.chiachen.network.model.response.NewNetworkModel
import javax.inject.Inject

class NewsNetworkDataMapper @Inject constructor() : Mapper<NewNetworkModel, NewDataModel> {
    override fun from(i: NewNetworkModel): NewDataModel {
        return NewDataModel(
            bannerUrl = i.bannerUrl.orEmpty(),
            description = i.description.orEmpty(),
            id = i.id.orEmpty(),
            rank = i.rank ?: Int.MAX_VALUE,
            timeCreated = i.timeCreated ?: Long.MAX_VALUE,
            title = i.title.orEmpty(),
        )
    }

    override fun to(o: NewDataModel): NewNetworkModel {
        return NewNetworkModel(
            bannerUrl = o.bannerUrl,
            description = o.description,
            id = o.id,
            rank = o.rank,
            timeCreated = o.timeCreated,
            title = o.title,
        )
    }
}