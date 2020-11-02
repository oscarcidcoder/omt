package com.omt.omtest.network.dto.containers

import com.omt.omtest.network.dto.VideoDTO
import com.omt.omtest.domain.Video

class VideosContainer(val response: List<VideoDTO>)

fun VideosContainer.asDomain(): List<Video> {
    return response.map {
        Video(it.metadata,
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


class VideoContainer(val response: VideoDTO)

fun VideoContainer.asDomain(): Video {
    return response.run {
        Video(metadata,
                keywordswords,
                year,
                assetExternalId,
                contentProvider,
                id,
                prName,
                name,
                shortName,
                discountId,
                advisories,
                attachments,
                description,
                duration,
                genreEntityList,
                plannedPublishDate,
                definition,
                windowEnd,
                encodings,
                externalId,
                removalDate,
                extrafields,
                contentProviderExternalId)
    }
}