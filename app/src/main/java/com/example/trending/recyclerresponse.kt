package com.example.trending

import com.google.gson.annotations.SerializedName

data class recyclerresponse {
    @SerializedName("albumId")
    var albumId: kotlin.Int,
    @SerializedName("id")
    var id: kotlin.Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String)
}