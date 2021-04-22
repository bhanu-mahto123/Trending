package com.example.trending

import android.telecom.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("photos")
    fun getTrending(): Call<List<recyclerresponse>>
}