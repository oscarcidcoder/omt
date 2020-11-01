package com.omt.omtest.db.dao

import androidx.room.*
import com.omt.omtest.db.entities.VideoDB

@Dao
interface VideoDAO {

    @Query("SELECT * FROM video")
    fun getAll(): List<VideoDB>

    @Query("SELECT * FROM video WHERE id = :videoID")
    fun getVideo(videoID: Int): VideoDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(video: VideoDB)

    @Query("DELETE FROM video")
    fun deleteAll()

    @Delete
    fun deleteVideo(video: VideoDB)
}