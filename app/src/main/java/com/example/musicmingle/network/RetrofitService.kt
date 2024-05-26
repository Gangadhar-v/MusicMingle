package com.example.musicmingle.network

import androidx.lifecycle.LiveData
import com.example.musicmingle.model.MusicData
import com.example.musicmingle.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface RetrofitService {

    @Headers(Constants.HEADER_1,Constants.HEADER_2)
    @GET(Constants.END_POINT)
    suspend fun getMusicData(@Query("q") artist:String): Response<MusicData>

}