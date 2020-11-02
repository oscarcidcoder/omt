package com.omt.omtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.omt.omtest.db.dao.VideoDAO
import com.omt.omtest.db.entities.VideoDB
import com.omt.omtest.domain.Video
import com.omt.omtest.domain.toDB
import com.omt.omtest.network.RequestRecommended
import com.omt.omtest.network.RequestService
import com.omt.omtest.network.dto.containers.asDomain

class SharedRepository(private val api: RequestService, private val recommendedApi: RequestRecommended, private val videoDAO: VideoDAO) {

    private lateinit var allVideos: List<Video>
    private val _mutableFavorite = MutableLiveData<List<Video?>>()

    val getFavoritesVideos: LiveData<List<Video?>>
        get() = _mutableFavorite

    suspend fun getAllVideos(): List<Video>  {
        allVideos = api.getVideos().asDomain()
        _mutableFavorite.postValue(getFavorites().value)
        return allVideos;
    }

    suspend fun getVideo(externalID: String) = api.getVideo(externalID).asDomain()

    suspend fun getRecommended(externalID: String) = recommendedApi.getRecommended(externalID).asDomain()

    private fun getFavorites() = Transformations.map(videoDAO.getAll()) { videosDB ->
        videosDB.map { videoDB -> allVideos.find { it.id == videoDB.id }?.apply {
            isFavorite = true
        }
        }
    }


    suspend fun saveFavorites() {
        _mutableFavorite.value?.let { videoDAO.insertAll(it.toDB()) }
         //videoDAO.insertAll(_mutableFavorite.value?.toDB()!!)
    }

    suspend fun saveFavoriteVideo(videoDB: VideoDB) {
        val listFavorite = _mutableFavorite.value?.toMutableList()
        if (listFavorite?.removeAll { it?.id == videoDB.id } == false) {
            val video = allVideos.find { it.id == videoDB.id }?.apply {
                isFavorite = true
            }
            listFavorite.add(video)
            _mutableFavorite.postValue(listFavorite)
        }

    }

    suspend fun saveFavoriteVideo2(video: VideoDB) {
        // si ya existe en la DB se borra ya que fue deseleccionado
        val isDeleted = videoDAO.getVideo(video.id)?.let {
            videoDAO.deleteVideo(it)
            true
        } ?: false

        if (!isDeleted)
            videoDAO.insertVideo(video)
    }

}