package com.omt.omtest.domain

import com.google.gson.annotations.SerializedName

data class Attachments (

    @SerializedName("responseElementType") val responseElementType : String,
    @SerializedName("assetId") val assetId : String,
    @SerializedName("name") val name : String,
    @SerializedName("assetName") val assetName : String,
    @SerializedName("value") val value : String
)