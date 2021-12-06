package com.example.jobbie.api

import com.example.jobbie.models.RemoteJob
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApi {
    @GET("remote-jobs?limit=100")
    fun getRemoteJob(): Call<RemoteJob>

    @GET("remote-jobs")
    fun searchRemoteJob(@Query("search") query: String?): Call<RemoteJob>
}