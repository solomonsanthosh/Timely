package com.example.todoapp

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id:Long,
    @SerializedName("email")
    val email:String)
