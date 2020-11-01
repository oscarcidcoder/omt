package com.omt.omtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class MainSharedViewModel constructor(private val repository: SharedRepository) : ViewModel() {


    fun getVideos() =
        liveData(Dispatchers.IO) {
            emit(repository.getAllVideos())
        }


    fun getVideo() {

    }

    fun getRecommended() {

    }

}