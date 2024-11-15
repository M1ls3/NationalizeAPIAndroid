package com.example.nationalizeapiandroid

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NationalizeApiService {
    @GET("/")
    fun getNationalizeData(@Query("name") name: String): Call<Nationalize>
}

// Клієнт Retrofit
object RetrofitInstance {
    private const val BASE_URL = "https://api.nationalize.io/"

    val api: NationalizeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NationalizeApiService::class.java)
    }
}
