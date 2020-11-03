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

    fun getVideo(externalID: String) {

    }

    fun getVideoWeb(externalID: String) {

    }

    fun getRecommended(externalID: String) {

    }

    fun setFavoriteVideo(id:Int, externalID: String) {
        val videos = _allVideos.value
        videos?.find { it.id == id }?.apply { isFavorite = !isFavorite }
        _mutableFavorite.value = videos?.filter { it.isFavorite }
        _allVideos.value = videos
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch(job + Dispatchers.IO) {
            repository.saveFavorites(_mutableFavorite.value ?: listOf())
        }
    }

}