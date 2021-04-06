package com.example.ourclass.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ourclass.HomeActivity
import com.example.ourclass.R
import com.example.ourclass.SearchActivity
import com.example.ourclass.adapter.HorizontalItemsRecylceAdapter
import com.example.ourclass.adapter.RecycleListAdapter
import com.example.ourclass.databinding.FragmentHomeBinding
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var Search_Input: EditText
    private lateinit var appContext: Context
    private var ItemArray: ArrayList<HorizontalItemsRecylceAdapter.Data> = ArrayList()
    private var categoryList: ArrayList<RecycleListAdapter.Data> = ArrayList()
    private lateinit var TopAdapter: HorizontalItemsRecylceAdapter
    private lateinit var CategoryAdapter: RecycleListAdapter
    private lateinit var DatabaseRef: DatabaseReference
    private var Array_key: ArrayList<String> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeActivity) appContext=context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        DatabaseRef = FirebaseDatabase.getInstance().getReference()
        //data for

        TopAdapter = HorizontalItemsRecylceAdapter(
            appContext,
            ItemArray,Array_key
        )
        CategoryAdapter = RecycleListAdapter(
            appContext,
            categoryList
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Search_Input = binding.CourseEditTextSearch
        Search_Input.setOnClickListener {
            val intent = Intent(appContext,SearchActivity::class.java)
            appContext.startActivity(intent)
        }

        //top recycle viwe
        val recycle = binding.CourseTopRecycleView
        recycle.layoutManager = LinearLayoutManager(appContext,LinearLayoutManager.HORIZONTAL,false)
        recycle.adapter = TopAdapter
        recycle.setHasFixedSize(true)

        DatabaseRef.child("Class_List").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    ItemArray.clear()
                    for(it in dataSnapshot.children){
                        val data = it.getValue(HorizontalItemsRecylceAdapter.Data::class.java)
                        ItemArray.add(data!!)
                        Array_key.add(it.key.toString())
                        TopAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(appContext,p0.toException().toString(),Toast.LENGTH_LONG).show()
            }
        })

        //first insert
        ItemArray.apply {
            add(HorizontalItemsRecylceAdapter.Data("","",0,0,0))
            add(HorizontalItemsRecylceAdapter.Data("","",0,0,0))
            add(HorizontalItemsRecylceAdapter.Data("","",0,0,0))
        }
        TopAdapter.notifyDataSetChanged()

        //category recycle view
        val caterecyle = binding.CourseCategoryRecycleView
        caterecyle.layoutManager = LinearLayoutManager(appContext,LinearLayoutManager.HORIZONTAL,false)
        categoryList.apply {
            add(RecycleListAdapter.Data("Tekonolgi"))
            add(RecycleListAdapter.Data("Bisnis"))
            add(RecycleListAdapter.Data("Psikologi"))
            add(RecycleListAdapter.Data("Seni"))
            add(RecycleListAdapter.Data("Argobisnis"))
            add(RecycleListAdapter.Data("Sastra"))
            add(RecycleListAdapter.Data("Kedokteran"))
        }
        caterecyle.setHasFixedSize(false)
        caterecyle.adapter=CategoryAdapter
        CategoryAdapter.notifyDataSetChanged()

        //free recycele view

        val freerecycel = binding.CourseFreeRecycleView
        freerecycel.layoutManager = LinearLayoutManager(appContext,LinearLayoutManager.HORIZONTAL,false)
        val freeList: ArrayList<HorizontalItemsRecylceAdapter.Data> = ArrayList()
        val adapterfree = HorizontalItemsRecylceAdapter(appContext,freeList,Array_key)
        freerecycel.adapter = adapterfree

        DatabaseRef.child("Class_List").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    freeList.clear()
                    for(it in dataSnapshot.children){
                        val data = it.getValue(HorizontalItemsRecylceAdapter.Data::class.java)
                        freeList.add(data!!)
                        //add array key
                        adapterfree.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(appContext,p0.toException().toString(),Toast.LENGTH_LONG).show()
            }
        })

        //first insert
        freeList.apply {
            add(HorizontalItemsRecylceAdapter.Data("","",0,0,0))
            add(HorizontalItemsRecylceAdapter.Data("","",0,0,0))
            add(HorizontalItemsRecylceAdapter.Data("","",0,0,0))
        }
        adapterfree.notifyDataSetChanged()

    }

}

