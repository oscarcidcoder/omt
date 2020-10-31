package com.omt.omtest.domain

import com.google.gson.annotations.SerializedName

data class GenreEntityList (

    @SerializedName("responseElementType") val responseElementType : String,
    @SerializedName("parentName") val parentName : String,
    @SerializedName("name") val name : String,
    @SerializedName("externalId") val externalId : String,
    @SerializedName("id") val id : Int
)