package com.raystatic.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {

    private fun getRetrofit(baseUrl:String):Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createBooksApi(baseUrl: String):BooksApi{
        val retrofit = getRetrofit(baseUrl)
        return retrofit.create(BooksApi::class.java)
    }

}