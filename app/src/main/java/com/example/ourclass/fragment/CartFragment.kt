package com.example.ourclass.fragment


import android.content.Context
import android.os.Bundle
import android.service.autofill.Dataset
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ourclass.HomeActivity
import com.example.ourclass.R
import com.example.ourclass.adapter.CartRecycleList
import com.example.ourclass.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    private lateinit var appContext: Context
    private lateinit var binding: FragmentCartBinding
    private lateinit var database: DatabaseReference
    private lateinit var DataShow: CartRecycleList
    private lateinit var DataListHolder: ArrayList<CartRecycleList.Data>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeActivity) appContext=context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataListHolder = ArrayList()
        DataShow = CartRecycleList(appContext,DataListHolder)
        database = FirebaseDatabase.getInstance().getReference()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycle = binding.CartRecycleViewData
        recycle.layoutManager = LinearLayoutManager(appContext,LinearLayoutManager.VERTICAL,false)
        recycle.adapter = DataShow
        recycle.setHasFixedSize(true)

        var user = ""

        if(FirebaseAuth.getInstance().currentUser!=null){
            user = FirebaseAuth.getInstance().currentUser?.uid.toString()
        }


        database.child("User_Order").child(user).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for(data in dataSnapshot.children){
                        val value = data.getValue(CartRecycleList.Data::class.java)
                        if(value!=null){
                            DataListHolder.add(value)
                            DataShow.notifyDataSetChanged()
                        }
                    }
                }else{
                    Toast.makeText(appContext,"Data no exist",Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(appContext,"You are don't have any class",Toast.LENGTH_SHORT).show()
            }
        })

        //DataShow.notifyDataSetChanged()

    }


}
