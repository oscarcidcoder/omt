package com.omt.omtest.domain

import com.google.gson.annotations.SerializedName

data class Images (

        @SerializedName("name") val name : String,
        @SerializedName("value") val value : String
)