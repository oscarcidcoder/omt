package com.omt.omtest.domain

import com.google.gson.annotations.SerializedName

data class Categories (

        @SerializedName("categoryName") val categoryName : String,
        @SerializedName("categoryId") val categoryId : String
)