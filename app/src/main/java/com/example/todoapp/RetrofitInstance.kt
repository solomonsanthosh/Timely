package com.example.todoapp


import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        const val BASE_URL = "http://192.168.0.101:8080"
        fun getRetrofitInstance():Retrofit{
            return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
        }
    }
}

