package com.omt.omtest.network.dto.containers

import com.omt.omtest.network.dto.Video

class VideoContainer(val response: List<Video>)

fun VideoContainer.asDomain(): List<com.omt.omtest.domain.Video> {
    return response.map {
        com.omt.omtest.domain.Video(it.metadata,
            it.keywordswords,
            it.year,
            it.assetExternalId,
            it.contentProvider,
            it.id,
            it.prName,
            it.name,
            it.shortName,
            it.discountId,
            it.advisories,
            it.attachments,
            it.description,
            it.duration,
            it.genreEntityList,
            it.plannedPublishDate,
            it.definition,
            it.windowEnd,
            it.encodings,
            it.externalId,
            it.removalDate,
            it.extrafields,
            it.contentProviderExternalId)
    }
}
