package com.omt.omtest.ui.detailvideo

import androidx.lifecycle.*
import com.omt.omtest.domain.RecommendedVideo
import com.omt.omtest.domain.Video
import com.omt.omtest.ui.SharedRepository
import com.omt.omtest.utils.externalToParam
import kotlinx.coroutines.*

class DetailViewModel constructor(private val externalID: String,
                                  private val isFavoriteInit: Boolean,
                                  private val repository: SharedRepository) : ViewModel() {

    val job = SupervisorJob()

    private val _video = MutableLiveData<Video>()
    val getVideo: LiveData<Video>
        get() = _video

    private val _allRecommended = MutableLiveData<List<RecommendedVideo>>()
    val getRecommended: LiveData<List<RecommendedVideo>>
        get() = _allRecommended

    init {
        getVideoData()
    }

    private fun getVideoData() {
        viewModelScope.launch(job + Dispatchers.IO) {
            val videoDeferred = async {
                repository.getVideo(externalID)
            }
            val video = videoDeferred.await()
            video.isFavorite = isFavoriteInit
            _video.postValue(video)
            _allRecommended.postValue(repository.getRecommended(video.assetExternalId.externalToParam()))
        }
    }

    fun setFavoriteState() {
        _video.value?.apply { isFavorite = !isFavorite }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch(job + Dispatchers.IO) {
            _video.value?.let { repository.saveVideoFavorite(it) }
        }
    }

}