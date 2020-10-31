package com.omt.omtest.domain

data class Video (
    val metadata : List<Metadata>,
    val keywordswords : String,
    val year : Int,
    val assetExternalId : String,
    val contentProvider : String,
    val id : Int,
    val prName : String,
    val name : String,
    val shortName : String,
    val discountId : String,
    val advisories : String,
    val attachments : List<Attachments>,
    val description : String,
    val duration : Long,
    val genreEntityList : List<GenreEntityList>,
    val plannedPublishDate : Long,
    val definition : String,
    val windowEnd : Long,
    val encodings : List<Encodings>,
    val externalId : String,
    val removalDate : Long,
    val extrafields : List<Extrafields>,
    val contentProviderExternalId : String,
    val isFavorite: Boolean = false
)