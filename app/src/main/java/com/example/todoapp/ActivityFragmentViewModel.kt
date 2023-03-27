package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityFragmentViewModel: ViewModel() {
    private var _activitylist = MutableLiveData(mutableListOf(Activity(0,"","",0)))
    val activityList : LiveData<MutableList<Activity>> = _activitylist

    fun updateActivity(activityItem:List<Activity>){

        var tempList = _activitylist.value
        if(tempList!![0].id == 0.toLong()){
            tempList = activityItem.toMutableList()
        } else {
            tempList?.addAll(activityItem)
        }

        _activitylist.value = tempList



    }

}