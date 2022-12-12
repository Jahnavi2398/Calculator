package com.example.calculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (val finalList: ArrayList<String>):RecyclerView.Adapter<ViewHolder>(){
    private var list:ArrayList<String> = finalList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.history_calc,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.result.text = list[position]
    }
    override fun getItemCount(): Int {
       return list.size
    }

}

class ViewHolder(listItem: View):RecyclerView.ViewHolder(listItem){
    var result = listItem.findViewById<TextView>(R.id.list_calc)
}