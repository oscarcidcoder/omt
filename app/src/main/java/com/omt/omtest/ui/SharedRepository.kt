package com.omt.omtest.ui

import com.omt.omtest.db.dao.VideoDAO
import com.omt.omtest.domain.Video
import com.omt.omtest.domain.toDB
import com.omt.omtest.network.RequestRecommended
import com.omt.omtest.network.RequestService
import com.omt.omtest.network.dto.containers.asDomain

class SharedRepository(private val api: RequestService, private val recommendedApi: RequestRecommended, private val videoDAO: VideoDAO) {


    suspend fun getAllVideos(): List<Video> = api.getVideos().asDomain()


    suspend fun getVideo(externalID: String) = api.getVideo(externalID).asDomain()

    suspend fun getRecommended(externalID: String) = recommendedApi.getRecommended(externalID).asDomain()

    fun getFavorites(allVideos: List<Video>): List<Video> {
        val videosDB = videoDAO.getAll()
        videosDB.forEach {videoDB ->
            allVideos.find { it.id == videoDB.id }?.apply { isFavorite = true }
        }

        return allVideos.filter { it.isFavorite }
    }

    suspend fun saveFavorites(favoritesVideos: List<Video?>) {
        if (favoritesVideos.isEmpty())
            videoDAO.deleteAll()
        else
            videoDAO.insertAll(favoritesVideos.toDB())
    }

}