package com.omt.omtest.network

import com.omt.omtest.network.dto.containers.RecommendedContainer
import com.omt.omtest.network.dto.containers.VideoContainer
import com.omt.omtest.network.dto.containers.VideosContainer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestService {

    @GET("GetUnifiedList?client=json&statuses=published&definitions=SD;HD;4K&external_category_id=SED_3880&filter_empty_categories=true")
    suspend fun getVideos(): VideosContainer

    @GET("GetVideo?client=json")
    suspend fun getVideo(@Query("external_id") externalID: String): VideoContainer
}

interface RequestRecommended {

    @GET("GetVideoRecommendationList?client=json&type=all&subscription=false&filter_viewed_content=true&max_results=10&" +
            "blend=ar_od_blend_2424video&max_pr_level=8&quality=SD,HD&services=2424VIDEO&")
    suspend fun getRecommended(@Query("params") externalID: String): RecommendedContainer
}

/**
 * Object to encapsule API requests
 */
object Service {

    private val retrofitInit = Retrofit.Builder()
            .baseUrl("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val retrofitRecommended = Retrofit.Builder()
            .baseUrl("https://smarttv.orangetv.orange.es/stv/api/reco/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val request = retrofitInit.create(RequestService::class.java)
    val requestRecommended = retrofitRecommended.create(RequestRecommended::class.java)
}