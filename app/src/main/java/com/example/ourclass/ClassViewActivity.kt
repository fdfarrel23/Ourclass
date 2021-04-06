package com.example.ourclass

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ourclass.adapter.ClassListView_Adapter
import com.example.ourclass.adapter.HorizontalItemsRecylceAdapter
import com.example.ourclass.databinding.ActivityClassViewBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.FieldPosition


class ClassViewActivity : YouTubeBaseActivity() {

    /*
    youtube api key:
    AIzaSyBBNA3R1ikoRF1YkpDErJPt_qL7CWqRQjE
    */

    /* FireBase Video URL:
       https://firebasestorage.googleapis.com/v0/b/ourclass-6c3bf.appspot.com/o/ourclass_folder%2Fitems_
       video%2Fvideo_class.mp4?alt=media&token=3cc956c6-bfb7-4ae7-b53c-5d8c607eea64
     */
    private var VideoDataHolder: Int = 0
    var Last_position: Int = 0

    private lateinit var binding: ActivityClassViewBinding
    private var Class_ListData: ArrayList<ClassListView_Adapter.Data> = ArrayList()
    private var Done_data: ArrayList<Char> = ArrayList()
    private var Class_ListAdapter: ClassListView_Adapter = ClassListView_Adapter(Last_position,this,Class_ListData,Done_data,this)
    lateinit var video_player: YouTubePlayerView
    private var DatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var Player: YouTubePlayer
    private lateinit var Key_Items: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_class_view)

        if(intent.extras!=null){
            Key_Items = intent.getStringExtra("Keys")
        }else{
            Toast.makeText(this,"Data Null",Toast.LENGTH_LONG).show()
        }
        //data load

        var user = ""
        if(FirebaseAuth.getInstance().currentUser!=null){
            user = FirebaseAuth.getInstance().currentUser!!.uid
        }
        DatabaseRef.child("Class_content").child(Key_Items).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    val first_data = dataSnapshot.child("1").child("video_id").getValue(String::class.java)
                    PlayVideo(first_data!!)
                    VideoDataHolder = 1
                    for(it in dataSnapshot.children){
                        val value = it.getValue(ClassListView_Adapter.Data::class.java)
                        Class_ListData.add(value!!)
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@ClassViewActivity,"Class Not Found!!", Toast.LENGTH_LONG).show()
            }
        })
        DatabaseRef.child("User_Data").child(user).child("My_class_data").child(Key_Items).child("Done_list").addValueEventListener(
                object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(this@ClassViewActivity,"Data Not Found",Toast.LENGTH_SHORT).show()
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                       if(p0.exists()){
                           val done_data = p0.getValue(String::class.java)
                           for (it in 0 until done_data!!.length){
                               if(done_data[it]!='|'){
                                   Done_data.add(done_data[it])
                               }
                           }
                       }
                        //set last position
                        for (it in 0 until Done_data.size){
                            if(Done_data[it]=='1'){
                                Last_position = it
                            }
                        }
                        Class_ListAdapter.notifyDataSetChanged()
                    }

                })

        //video set
        video_player = binding.ClassVidoView
        //
        binding.ClassShowToolbar.setNavigationOnClickListener {
            finish()
        }


        binding.ClassListView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.ClassListView.adapter = Class_ListAdapter

        //done button handeler
        fun genrate(): String{
            var finish_Data: String = ""
            Done_data.set(Last_position,'1')
            for (d in 0 until Done_data.size){
                finish_Data = finish_Data+Done_data[d]+"|"
            }
            return finish_Data
        }
        binding.ClassViewDone.setOnClickListener {
            var data_done = genrate()
            DatabaseRef.child("User_Data").child(user).child("My_class_data").child(Key_Items).child("Done_list").setValue(data_done)
            Done_data.clear()
            for(i in 0 until data_done.length){
                if(data_done[i]!='|') Done_data.add(data_done[i])
            }
            Class_ListAdapter.notifyDataSetChanged()
            recreate()
        }
    }

    fun PlayVideo(VidioId: String){
        video_player.initialize("AIzaSyBBNA3R1ikoRF1YkpDErJPt_qL7CWqRQjE",
                object : YouTubePlayer.OnInitializedListener {

                    override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                                         youTubePlayer: YouTubePlayer, b: Boolean) {
                        Player = youTubePlayer
                        Player.cueVideo(VidioId)
                    }


                    override fun onInitializationFailure(provider: YouTubePlayer.Provider,
                                                         youTubeInitializationResult: YouTubeInitializationResult) {
                    }
                })
    }

    fun LoadAntoherVideo(VidioId: String,position: Int){
        Last_position = position
        Player.cueVideo(VidioId)
    }
}


