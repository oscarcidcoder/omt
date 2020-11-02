package com.omt.omtest.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.omt.omtest.db.entities.VideoDB

@Dao
interface VideoDAO {

    @Query("SELECT * FROM video")
    fun getAll(): LiveData<List<VideoDB>>

    @Query("SELECT * FROM video WHERE id = :videoID")
    fun getVideo(videoID: Int): VideoDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: VideoDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos: List<VideoDB>)

    @Query("DELETE FROM video")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteVideo(video: VideoDB)
}