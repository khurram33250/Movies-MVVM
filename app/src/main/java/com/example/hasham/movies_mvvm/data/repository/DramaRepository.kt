package com.example.hasham.movies_mvvm.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.hasham.movies_mvvm.data.models.DramaResponse
import com.example.hasham.movies_mvvm.data.remote.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Khurram on 08-Dec-17.
 */

class DramaRepository(private var dramaService: API.Endpoints) {


    init {

        dramaService = dramaService
    }

    fun getDramas(page: String): LiveData<DramaResponse> {

        val data = MutableLiveData<DramaResponse>()

        dramaService.getDramas(page,"vote_count.gte", "18", "10" ).enqueue(object : Callback<DramaResponse> {

            override fun onFailure(call: Call<DramaResponse>?, t: Throwable?) {


            }

            override fun onResponse(call: Call<DramaResponse>, response: Response<DramaResponse>) {

                data.value = response.body()
            }
        })

        return data
    }
}