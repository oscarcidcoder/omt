package com.omt.omtest.ui

import android.util.Log
import androidx.lifecycle.*
import com.omt.omtest.domain.Video
import kotlinx.coroutines.*

class MainSharedViewModel constructor(private val repository: SharedRepository) : ViewModel() {

    private val job = SupervisorJob()

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
            Log.i("VIEW_MODEL", "getVideos: All SIZE-> ${videos.size}")
            Log.i("VIEW_MODEL", "getVideos: Fav SIZE-> ${favoritesVideos.size}")
            _allVideos.postValue(videos)
            _mutableFavorite.postValue(favoritesVideos)
        }
    }

    /**
     * Find video in internal list without go to network
     */
    fun getVideo(externalID: String) = _allVideos.value?.find { it.externalId == externalID }

    /**
     * Find video in network
     */
    fun getVideoWeb(externalID: String) = liveData(job + Dispatchers.IO) {
        emit(repository.getVideo(externalID))
    }

    fun getRecommended(externalID: String) = liveData(job + Dispatchers.IO) {
        emit(repository.getRecommended(externalID))
    }

    fun updateVideosState() {
        viewModelScope.launch(job + Dispatchers.IO) {
            val videos = _allVideos.value
            delay(500)
            val allVideosBind = videos?.let { repository.getFavorites2(it) }
            Log.i("VIEW_MODEL", "updateVideosState: All SIZE-> ${allVideosBind?.size}")
            val favorites = allVideosBind?.filter { it.isFavorite }
            Log.i("VIEW_MODEL", "updateVideosState: Fav SIZE-> ${favorites?.size}")
            _allVideos.postValue(allVideosBind)
            _mutableFavorite.postValue(favorites)
        }
    }

    fun setFavoriteVideo(id:Int, externalID: String) {
        val videos = _allVideos.value
        videos?.find { it.id == id }?.apply { isFavorite = !isFavorite }
        _mutableFavorite.value = videos?.filter { it.isFavorite }
        _allVideos.value = videos
    }

    fun saveFavorites() {
        viewModelScope.launch(job + Dispatchers.IO) {
            repository.saveFavorites(_mutableFavorite.value ?: listOf())
        }
    }

    /**
     * OnCleared call, save all favorite data
     */
    override fun onCleared() {
        super.onCleared()
        saveFavorites()
    }

}