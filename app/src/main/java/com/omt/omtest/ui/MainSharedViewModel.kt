package com.omt.omtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.omt.omtest.db.entities.VideoDB
import kotlinx.coroutines.*

class MainSharedViewModel constructor(private val repository: SharedRepository) : ViewModel() {

    val job = SupervisorJob()

    fun getVideos() =
        liveData(job + Dispatchers.IO) {
            emit(repository.getAllVideos())
        }


    fun getVideo() {

    }

    fun getRecommended() {

    }

    fun getFavoritesVideos() = liveData { emitSource(repository.getFavoritesVideos) }


    fun setFavoriteVideo(id:Int, externalID: String) {
        val newFavorite = VideoDB(id,externalID)
        viewModelScope.launch(job + Dispatchers.IO) {
            repository.saveFavoriteVideo(newFavorite)
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch(job + Dispatchers.IO) {
            repository.saveFavorites()
        }
    }

}