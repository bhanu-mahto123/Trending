package com.example.trending.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "repositories")
data class GitRepoData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @SerializedName("author")
    var author: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("avatar")
    var avatar: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("language")
    var language: String?,
    @SerializedName("languageColor")
    var languageColor: String?,
    @SerializedName("stars")
    var stars: String,
    @SerializedName("forks")
    var forks: String,
    var isExpanded: Boolean = false,
    var createdAt: Long
)