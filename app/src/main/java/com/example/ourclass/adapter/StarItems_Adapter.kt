package com.example.ourclass.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ourclass.R

class StarItems_Adapter (val context: Context,val data: ArrayList<Data>): RecyclerView.Adapter<StarItems_Adapter.DataHolder>(){
    data class Data(val image: Int)

    inner class DataHolder(view: View): RecyclerView.ViewHolder(view){
        val star = view.findViewById<ImageView>(R.id.Star_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(LayoutInflater.from(context).inflate(R.layout.star_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val(image) = data[position]
        holder.star.setImageResource(image)
    }
}