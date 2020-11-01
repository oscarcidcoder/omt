package com.omt.omtest.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class VideoDB(@PrimaryKey val id: Int
                   , val externalID: String)