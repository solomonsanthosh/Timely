package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerViewAdaptor(val activityList:List<Activity>):RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflator = LayoutInflater.from(parent.context)
        val listItem = layoutInflator.inflate(R.layout.list_item,parent,false)
        return MyViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val activity = activityList[position]
        holder.bind(activity)

    }

}

class MyViewHolder(val view:View):RecyclerView.ViewHolder(view){
    fun bind(activity:Activity){
        val title = view.findViewById<TextView>(R.id.titleContent)
        title.text = activity.title
        val content = view.findViewById<TextView>(R.id.content)
        content.text = activity.content
    }

}