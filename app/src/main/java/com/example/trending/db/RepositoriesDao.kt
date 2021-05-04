package com.example.trending.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.trending.data.GitRepoData


@Dao
interface RepositoriesDao {

    @Query("SELECT * FROM repositories")
    fun getRepositories(): LiveData<List<GitRepoData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(repos: List<GitRepoData>): List<Long>

    @Query("DELETE FROM repositories")
    fun deleteAll()

    @Transaction
    fun deleteAndInsertRepos(repos: List<GitRepoData>) {
        deleteAll()
        insertRepositories(repos)
    }

    @Transaction
    fun deleteAndInsertWithTimeStamp(repos: List<GitRepoData>) {
        deleteAndInsertRepos(repos.apply {
            for (data in this) {
                data.createdAt = System.currentTimeMillis()
            }
        })
    }
}