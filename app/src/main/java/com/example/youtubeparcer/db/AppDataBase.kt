package com.example.youtubeparcer.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.youtubeparcer.model.PlaylistModel

@Database(entities = [
PlaylistModel::class],
    version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun ytVideoDao(): YoutubeDao
}