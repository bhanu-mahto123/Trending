package com.example.trending.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trending.data.GitRepoData


@Database(entities = [GitRepoData::class], version = 1)
abstract class RepositoriesDatabase : RoomDatabase() {

    abstract fun repositoriesDao(): RepositoriesDao
}