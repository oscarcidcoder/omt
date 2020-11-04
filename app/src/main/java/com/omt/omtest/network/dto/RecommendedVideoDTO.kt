package com.omt.omtest.network.dto

import com.google.gson.annotations.SerializedName
import com.omt.omtest.domain.Availabilities
import com.omt.omtest.domain.Genres
import com.omt.omtest.domain.Images

data class RecommendedVideoDTO (

        @SerializedName("ratersCount") val ratersCount : Int,
        @SerializedName("images") val images : List<Images>,
        @SerializedName("prLevel") val prLevel : Int,
        @SerializedName("availabilities") val availabilities : List<Availabilities>,
        @SerializedName("prName") val prName : String,
        @SerializedName("rating") val rating : Float,
        @SerializedName("type") val type : String,
        @SerializedName("ContentProperties") val contentProperties : List<String>,
        @SerializedName("recommendationReasons") val recommendationReasons : List<String>,
        @SerializedName("genres") val genres : List<Genres>,
        @SerializedName("name") val name : String,
        @SerializedName("externalContentId") val externalContentId : String,
        @SerializedName("id") val id : Int,
        @SerializedName("contentType") val contentType : String
)