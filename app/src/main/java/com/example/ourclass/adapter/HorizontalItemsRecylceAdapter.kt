package com.example.ourclass.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.ourclass.ClassViewActivity
import com.example.ourclass.Class_Detail_Activity
import com.example.ourclass.HomeActivity
import com.example.ourclass.R
import com.google.firebase.storage.FirebaseStorage
import java.text.DecimalFormat

class HorizontalItemsRecylceAdapter (val context: Context, val data: ArrayList<Data>,val items_key: ArrayList<String>):RecyclerView.Adapter<HorizontalItemsRecylceAdapter.DataHolder>(){
    data class Data(val ImageHeader: String="",val Title: String="",val price: Int=0,val star: Int=0,val vote_count: Int=0)

    //val storage = FirebaseStorage.getInstance()

    inner class DataHolder(view: View): RecyclerView.ViewHolder(view){
        val items_box = view.findViewById<LinearLayout>(R.id.Items_Box)
        val star_list = view.findViewById<RecyclerView>(R.id.Items_star_show)
        val image = view.findViewById<ImageView>(R.id.Items_ImageView)
        val title = view.findViewById<TextView>(R.id.Items_Title)
        val count = view.findViewById<TextView>(R.id.Items_Count)
        val price = view.findViewById<TextView>(R.id.Items_Price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(LayoutInflater.from(context).inflate(R.layout.items_box_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        var(image,item_title,price,star,vote_count) = data[position]

       //var reference = storage.getReferenceFromUrl(image)

        val dataStar = ArrayList<StarItems_Adapter.Data>()
        val starAdapter = StarItems_Adapter(context,dataStar)

        for (d in 0 until star){
            dataStar.add(StarItems_Adapter.Data(R.drawable.ic_shapes_and_symbols))
        }
        val starlayoutmanager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        holder.star_list.layoutManager = starlayoutmanager
        holder.star_list.adapter = starAdapter
        starAdapter.notifyDataSetChanged()

        Glide.with(context)
            .load(image).apply { RequestOptions().override(160,100) }
            .into(holder.image)
        //holder.image.setImageResource(R.drawable.content_test)
        if(price != 0){
            //change color
            holder.count.setBackgroundColor(Color.TRANSPARENT)
            holder.title.setBackgroundColor(Color.TRANSPARENT)
            holder.price.setBackgroundColor(Color.TRANSPARENT)
            holder.star_list.setBackgroundColor(Color.TRANSPARENT)
            //change value
            holder.count.text="("+vote_count.toString()+")"
            var recreat_string = ""
            if(item_title.length>=35){
                recreat_string=""
                for (d in 0 until 35){
                    recreat_string = recreat_string+item_title[d]
                }
                holder.title.text=recreat_string+"..."
            }else{
                holder.title.text=item_title
            }
            val formater = DecimalFormat("#,###")
            holder.price.text=formater.format(price).toString()+" IDR"
            if(position==0){
                val param = holder.items_box.layoutParams as RecyclerView.LayoutParams
                param.setMargins(40,0,5,0)
                holder.items_box.layoutParams = param
            }

            holder.items_box.setOnClickListener {
                val intent = Intent(context,Class_Detail_Activity::class.java)
                intent.putExtra("Key",items_key[position].toString())
                context.startActivity(intent)
            }
        }
    }
}