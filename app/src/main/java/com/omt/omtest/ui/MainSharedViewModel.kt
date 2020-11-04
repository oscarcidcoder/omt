package com.omt.omtest.ui

import androidx.lifecycle.*
import com.omt.omtest.domain.Video
import kotlinx.coroutines.*

class MainSharedViewModel constructor(private val repository: SharedRepository) : ViewModel() {

    private val job = SupervisorJob()

    private var videoClicked= Pair(-1,-1)
    private val _updateAdapterData = MutableLiveData<Pair<Int,Int>>()
    val updateAdapterData: LiveData<Pair<Int,Int>>
        get() = _updateAdapterData

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

    fun updateVideosState() {
        val positionToUpdate = videoClicked
        videoClicked = Pair(-1,-1)
        if (positionToUpdate.first != -1) {
            val videos = _allVideos.value
            val video = videos?.firstOrNull { it.id == positionToUpdate.first }

            video?.let {
                viewModelScope.launch(job + Dispatchers.IO) {
                    delay(500)
                    val videoDB = async(job) {
                        it.id.let { repository.getVideoDB(it) }
                    }.await()

                    val positionInAll = videos.indexOf(it)
                    videos[positionInAll].isFavorite = videoDB != null
                    val favoriteList = videos.filter { it.isFavorite }
                    val positionInFavorite = favoriteList.indexOf(it)
                    _allVideos.postValue(videos)
                    _mutableFavorite.postValue(favoriteList)
                    _updateAdapterData.postValue(Pair(positionInAll,positionInFavorite))
                }
            }

        }

        /* viewModelScope.launch(job + Dispatchers.IO) {
            val videos = _allVideos.value
            delay(500)
            val allVideosBind = videos?.let { repository.getFavorites2(it) }
            val favorites = allVideosBind?.filter { it.isFavorite }
            _allVideos.postValue(allVideosBind)
            _mutableFavorite.postValue(favorites)
        } */
    }

    fun setVideoClickPosition(videoID: Int, position: Int) {
        videoClicked = Pair(videoID,position)
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