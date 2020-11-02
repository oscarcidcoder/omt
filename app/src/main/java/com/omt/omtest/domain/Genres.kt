package com.omt.omtest.domain

import com.google.gson.annotations.SerializedName

data class Genres (

        @SerializedName("name") val name : String,
        @SerializedName("externalId") val externalId : String,
        @SerializedName("id") val id : String
)