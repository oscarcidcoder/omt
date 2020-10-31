package com.omt.omtest.domain

import com.google.gson.annotations.SerializedName

data class Metadata (
    @SerializedName("responseElementType") val responseElementType : String,
    @SerializedName("name") val name : String,
    @SerializedName("value") val value : String
)