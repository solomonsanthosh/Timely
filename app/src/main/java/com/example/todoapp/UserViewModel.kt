package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private var userData = MutableLiveData(User(0,""))
    val user : LiveData<User> = userData
    fun updateUser (id:Long,email:String) {
        userData.value = User(id,email)

    }
//    fun getUser () {
//        if(user!= null) {
//            return user
//        }
//
//    }
}