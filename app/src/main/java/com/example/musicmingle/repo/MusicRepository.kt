package com.example.musicmingle.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.musicmingle.model.MusicData
import com.example.musicmingle.network.RetrofitInstance
import com.example.musicmingle.network.RetrofitService
import retrofit2.Response

class MusicRepository {
    private val retrofitService = RetrofitInstance.retrofitService

    suspend fun getMusicData(artist: String): Result<MusicData> {
        return try{
            val response = retrofitService.getMusicData(artist)
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Error:${response.code()} ${response.message()}"))
            }
        }catch(e:Exception){
            Result.failure(e)
        }
    }

}
