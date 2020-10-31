package com.omt.omtest.domain

import com.google.gson.annotations.SerializedName

data class AllowedTerminalCategories (

    @SerializedName("responseElementType") val responseElementType : String,
    @SerializedName("maxTerminalsOfNonOperator") val maxTerminalsOfNonOperator : Int,
    @SerializedName("maxTerminals") val maxTerminals : Int,
    @SerializedName("name") val name : String,
    @SerializedName("externalId") val externalId : String
)