package com.omt.omtest.domain

data class RecommendedVideo (
        val ratersCount : Int,
        val images : List<Images>,
        val availabilities : List<Availabilities>,
        val prName : String,
        val rating : Int,
        val genres : List<Genres>,
        val name : String,
        val externalContentId : String,
        val id : Int
)