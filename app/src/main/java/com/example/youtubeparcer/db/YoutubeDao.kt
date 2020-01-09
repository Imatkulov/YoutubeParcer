package com.example.youtubeparcer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.youtubeparcer.model.PlaylistModel

@Dao
interface YoutubeDao {

    @Insert
    suspend fun insertAllPlayList(items: PlaylistModel)

    @Query("SELECT * FROM play_list")
    suspend fun getALLPlayList(): PlaylistModel
}