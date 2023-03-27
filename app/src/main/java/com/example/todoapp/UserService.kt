package com.example.todoapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/api/user/{email}")
    suspend fun getUser(@Path("email") email:String): Response<User>
}