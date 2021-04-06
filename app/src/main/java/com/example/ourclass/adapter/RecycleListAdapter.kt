package com.example.ourclass.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ourclass.R

class RecycleListAdapter (val context: Context, val data: ArrayList<Data>):RecyclerView.Adapter<RecycleListAdapter.DataHolder>(){
    data class Data(val title: String)

    inner class DataHolder(view: View):RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.Category_Text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(LayoutInflater.from(context).inflate(R.layout.category_list,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val (text) = data[position]
        holder.title.text=text
    }


}