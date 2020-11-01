package com.omt.omtest.ui

import com.omt.omtest.db.dao.VideoDAO
import com.omt.omtest.network.RequestRecommended
import com.omt.omtest.network.RequestService
import com.omt.omtest.network.dto.containers.asDomain

class SharedRepository(private val api: RequestService, private val recommendedApi: RequestRecommended, private val videoDAO: VideoDAO) {

    suspend fun getAllVideos() = api.getVideos().asDomain()

    suspend fun getVideo(externalID: String) {
        api.getVideo(externalID)
    }

    suspend fun getRecommended(externalID: String) {
        recommendedApi.getRecommended(externalID)
    }
}