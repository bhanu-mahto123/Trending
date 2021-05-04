package com.example.trending.data

import androidx.lifecycle.LiveData
import com.example.trending.data.network.Resource
import retrofit2.http.GET


interface DataApi {

    @GET("repositories?language=&since=daily&spoken_language_code=")
    fun fetchRepositories(): LiveData<Resource<List<GitRepoData>>>
}