package com.omt.omtest.ui.detailvideo

import androidx.lifecycle.*
import com.omt.omtest.domain.Video
import com.omt.omtest.ui.SharedRepository
import kotlinx.coroutines.*

class DetailViewModel constructor(private val repository: SharedRepository, private val allVideos: List<Video>) : ViewModel() {

    val job = SupervisorJob()

    /**
     * Find video in internal list without go to network
     */
    fun getVideo(externalID: String) = allVideos.find { it.externalId == externalID }

    /**
     * Find video in network
     */
    fun getVideoWeb(externalID: String) = liveData(job + Dispatchers.IO) {
        emit(repository.getVideo(externalID))
    }

    fun getRecommended(externalID: String) = liveData(job + Dispatchers.IO) {
        emit(repository.getRecommended(externalID))
    }

}