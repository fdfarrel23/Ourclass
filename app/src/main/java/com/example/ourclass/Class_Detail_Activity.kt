package com.example.ourclass

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ourclass.adapter.ClassListView_Adapter
import com.example.ourclass.adapter.StarItems_Adapter
import com.example.ourclass.databinding.ActivityClassDetailBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.firebase.database.*
import java.text.DecimalFormat

class Class_Detail_Activity : YouTubeBaseActivity() {

    private lateinit var DataRef: DatabaseReference

    private lateinit var binding: ActivityClassDetailBinding
    private lateinit var Key_Items: String
    private lateinit var video_player: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_class__detail_)

        DataRef = FirebaseDatabase.getInstance().reference

        video_player = binding.ClassDetailYoutubeplayer

        if (intent.extras!=null){
            Key_Items = intent.getStringExtra("Key")
        }else{
            Toast.makeText(this,"Data Null",Toast.LENGTH_LONG).show()
        }

        val layout_manager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val dataStar = ArrayList<StarItems_Adapter.Data>()
        val starAdapter = StarItems_Adapter(this,dataStar)
        binding.ClassDetailStarsview.layoutManager = layout_manager
        binding.ClassDetailStarsview.adapter = starAdapter

        DataRef.child("Class_List").child(Key_Items).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@Class_Detail_Activity,"Data Not Found!!",Toast.LENGTH_LONG).show()
            }
            override fun onDataChange(p0: DataSnapshot) {
               if(p0.exists()){
                   val star = p0.child("star").getValue(Int::class.java)
                   for(d in 0 until star!!){
                       dataStar.add(StarItems_Adapter.Data(R.drawable.ic_shapes_and_symbols))
                   }
                   starAdapter.notifyDataSetChanged()
                   var format = DecimalFormat("#,###")
                   binding.ClassDetailPrice.text = format.format(p0.child("price").getValue(Int::class.java)).toString()+" IDR"
                   binding.ClassDetailImageHeader
                   binding.ClassOwnerName.text = p0.child("Class_account").getValue(String::class.java)!!
                   binding.ClassDetailTitle.text = p0.child("Title").getValue(String::class.java)!!
                   binding.ClassDetailStudentJoin.text = p0.child("Student_join").getValue(Int::class.java)!!.toString()+" Bergabung"
                   binding.ClassDetailReview.text = p0.child("review").childrenCount.toInt().toString()+" Review"
                   if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                       binding.ClassDetailDescription.text = Html.fromHtml(p0.child("description").getValue(String::class.java)!!,Html.FROM_HTML_MODE_COMPACT)
                   }else{
                       binding.ClassDetailDescription.text = Html.fromHtml(p0.child("description").getValue(String::class.java)!!)
                   }
                   var image = p0.child("ImageHeader").getValue(String::class.java)
                   Glide.with(this@Class_Detail_Activity)
                           .load(image).apply { RequestOptions().override(250,250) }
                           .into(binding.ClassDetailImageHeader)
               }
            }
        })

        DataRef.child("Class_content").child(Key_Items).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    val first_data = dataSnapshot.child("1").child("video_id").getValue(String::class.java)
                    PlayVideo(first_data!!)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@Class_Detail_Activity,"Class Not Found!!", Toast.LENGTH_LONG).show()
            }
        })



        binding.JoinButton.setOnClickListener {
            val intent = Intent(this,ClassViewActivity::class.java)
            intent.putExtra("Keys",Key_Items)
            startActivity(intent)
        }
        binding.ClassToolbar.setNavigationOnClickListener {
            finish()
        }
    }
    fun PlayVideo(VidioId: String){
        video_player.initialize("AIzaSyBBNA3R1ikoRF1YkpDErJPt_qL7CWqRQjE",
                object : YouTubePlayer.OnInitializedListener {

                    override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                                         youTubePlayer: YouTubePlayer, b: Boolean) {
                        youTubePlayer.cueVideo(VidioId)
                    }


                    override fun onInitializationFailure(provider: YouTubePlayer.Provider,
                                                         youTubeInitializationResult: YouTubeInitializationResult) {
                    }
                })
    }
}
