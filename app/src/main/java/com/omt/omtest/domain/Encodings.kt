package com.omt.omtest.domain

import com.google.gson.annotations.SerializedName

data class Encodings (

    @SerializedName("responseElementType") val responseElementType : String,
    @SerializedName("name") val name : String
)