package com.omt.omtest.ui

import androidx.lifecycle.*
import com.omt.omtest.domain.Video
import kotlinx.coroutines.*

class MainSharedViewModel constructor(private val repository: SharedRepository) : ViewModel() {

    val job = SupervisorJob()

    private val _allVideos = MutableLiveData<List<Video>>()
    val getAllVideos: LiveData<List<Video>>
        get() = _allVideos

    private val _mutableFavorite = MutableLiveData<List<Video?>>()
    val getFavoritesVideos: LiveData<List<Video?>>
        get() = _mutableFavorite

    init {
        getVideos()
    }

    private fun getVideos() {
        viewModelScope.launch(job + Dispatchers.IO) {
            val videos = repository.getAllVideos()
            val favoritesVideos = repository.getFavorites(videos)
            _allVideos.postValue(videos)
            _mutableFavorite.postValue(favoritesVideos)
        }
    }

    /**
     * Find video in internal list without go to network
     */
    fun getVideo(externalID: String) = _allVideos.value?.find { it.externalId == externalID }

    fun getVideo2(externalID: String): Video? {
        val ver = getAllVideos.value?.get(0)
        val result = _allVideos.value?.find { it.externalId == externalID }
        return result
    }

    /**
     * Find video in network
     */
    fun getVideoWeb(externalID: String) = liveData(job + Dispatchers.IO) {
        emit(repository.getVideo(externalID))
    }

    fun getRecommended(externalID: String) = liveData(job + Dispatchers.IO) {
        emit(repository.getRecommended(externalID))
    }

    fun setFavoriteVideo(id:Int, externalID: String) {
        val videos = _allVideos.value
        videos?.find { it.id == id }?.apply { isFavorite = !isFavorite }
        _mutableFavorite.value = videos?.filter { it.isFavorite }
        _allVideos.value = videos
    }

    /**
     * OnCleared call, save all favorite data
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch(job + Dispatchers.IO) {
            repository.saveFavorites(_mutableFavorite.value ?: listOf())
        }
    }

}