package com.omt.omtest.network.dto.containers

import com.omt.omtest.domain.RecommendedVideo
import com.omt.omtest.network.dto.RecommendedVideoDTO

class RecommendedContainer(val response: List<RecommendedVideoDTO>)

fun RecommendedContainer.asDomain(): List<RecommendedVideo> {
    return response.map {
        RecommendedVideo(
                it.ratersCount,
                it.images,
                it.availabilities,
                it.prName,
                it.rating,
                it.genres,
                it.name,
                it.externalContentId,
                it.id
        )
    }
}