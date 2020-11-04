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

    suspend fun getRecommended(assetExternalId: String) = recommendedApi.getRecommended(assetExternalId).asDomain()

    fun getFavorites(allVideos: List<Video>): List<Video> {
        val videosDB = videoDAO.getAll()
        videosDB.forEach {videoDB ->
            allVideos.find { it.id == videoDB.id }?.apply { isFavorite = true }
        }

        return allVideos.filter { it.isFavorite }
    }

    fun getFavorites2(allVideos: List<Video>): List<Video> {
        val videosDB = videoDAO.getAll()
        videosDB.forEach {videoDB ->
            allVideos.find { it.id == videoDB.id }?.apply { isFavorite = true }
        }

        return allVideos
    }


    suspend fun saveVideoFavorite(video: Video) {
        if (video.isFavorite)
            videoDAO.insertVideo(video.toDB())
        else
            videoDAO.deleteVideo(video.toDB())
    }

    suspend fun saveFavorites(favoritesVideos: List<Video?>) {
        videoDAO.deleteAll()
        if (favoritesVideos.isNotEmpty())
            videoDAO.insertAll(favoritesVideos.toDB())
    }

}