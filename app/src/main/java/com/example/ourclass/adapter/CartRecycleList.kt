package com.example.ourclass.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ourclass.R
import com.google.firebase.storage.FirebaseStorage

class CartRecycleList(val context: Context,val data: ArrayList<Data>): RecyclerView.Adapter<CartRecycleList.DataHolder>(){

    // private val storage = FirebaseStorage.getInstance()//

    data class Data(val ImageUrl: String="",val Title: String="", val price: Int=0, val status: Int=0)

    inner class DataHolder(view: View): RecyclerView.ViewHolder(view){
        val image_view = view.findViewById<ImageView>(R.id.Cart_items_image)
        val title_view = view.findViewById<TextView>(R.id.Cart_item_title)
        val price_view = view.findViewById<TextView>(R.id.Cart_item_price)
        val status_view = view.findViewById<TextView>(R.id.Cart_item_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(LayoutInflater.from(context).inflate(R.layout.cart_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val (image,title,price,status) = data[position]

        //val reference = storage.getReferenceFromUrl(image)

        Glide.with(context)
            .load(image).apply { RequestOptions().override(160,100) }
            .into(holder.image_view)
        holder.title_view.text = title
        holder.price_view.text = price.toString()+" IDR"
        when(status){
            1->{
                holder.status_view.text = "Lunas"
            }
            2 ->{
                holder.status_view.text = "Menunggu Pembayaran"
            }
        }
    }
}