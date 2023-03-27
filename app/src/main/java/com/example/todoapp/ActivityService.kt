package com.example.todoapp

import retrofit2.Call
import retrofit2.Response

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivityService {
    @GET("/api/activity/{id}")
    suspend fun getActivities(@Path("id") id:Long): Response<List<Activity>>

    @FormUrlEncoded
    @POST("/api/activity")
    suspend fun postActivity(@Field("title") title:String ,@Field("content") content:String , @Field("user_id") user_id:Long):Call<Activity>
}