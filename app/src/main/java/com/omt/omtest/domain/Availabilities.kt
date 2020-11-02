package com.omt.omtest.domain

import com.google.gson.annotations.SerializedName

data class Availabilities (
        @SerializedName("images") val images : List<Images>,
        @SerializedName("videoId") val videoId : String,
        @SerializedName("startTime") val startTime : Int,
        @SerializedName("endTime") val endTime : Int,
        @SerializedName("categories") val categories : List<Categories>,
        @SerializedName("serviceId") val serviceId : String
)