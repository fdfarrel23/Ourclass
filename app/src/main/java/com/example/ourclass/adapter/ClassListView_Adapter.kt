package com.example.ourclass.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ourclass.ClassViewActivity
import com.example.ourclass.R

class ClassListView_Adapter (var lastView: Int,val context: Context,val data: ArrayList<Data>,val data_Done: ArrayList<Char>,val activity: ClassViewActivity)
    : RecyclerView.Adapter<ClassListView_Adapter.DataHolder>(){
    data class Data(val title: String="",val video_id: String="")

    var lastHolder: Int = lastView

    inner class DataHolder(view: View): RecyclerView.ViewHolder(view){
        val class_data = view.findViewById<TextView>(R.id.Class_List_Name)
        val class_button = view.findViewById<LinearLayout>(R.id.Class_list_button)
        val class_checklist = view.findViewById<ImageView>(R.id.Checklist_class_indicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(LayoutInflater.from(context).inflate(R.layout.class_layout_list,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val(text,ID) = data[position]
        holder.class_data.text = (position+1).toString()+" "+text
        holder.class_button.setOnClickListener {
            Toast.makeText(context,lastView.toString(), Toast.LENGTH_SHORT).show()
            lastHolder=position
            notifyDataSetChanged()
            activity.LoadAntoherVideo(ID,position)
        }
        fun class_done(){
            if(data_Done.size!=0){
                if(data_Done[position]=='0'){
                    holder.class_checklist.visibility = View.GONE
                }
            }else{
                android.os.Handler().postDelayed({
                    class_done()
                },1000)
            }
        }
        class_done()
        if(lastHolder==position){
            holder.class_button.setBackgroundColor(Color.parseColor("#969696"))
            holder.class_data.setTextColor(Color.parseColor("#ECECEC"))
        }else{
            holder.class_button.setBackgroundColor(Color.parseColor("#BEBEBE"))
            holder.class_data.setTextColor(Color.parseColor("#4A4A4A"))
        }
    }
}