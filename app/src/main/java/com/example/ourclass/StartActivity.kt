package com.example.ourclass

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class StartActivity : AppCompatActivity() {

    //val database: DbHelper = DbHelper(this)
    private lateinit var firebaseuser: FirebaseUser
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val decorView = window.decorView
        val uiOption = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOption
        firebaseAuth = FirebaseAuth.getInstance()

    }
    override fun onResume() {
        super.onResume()
        var intent = Intent(this,TabActivity::class.java)
        //val res: Cursor = database.checkingDataGet()
        Handler().postDelayed({
            if(firebaseAuth.currentUser==null){
                //start some activity
                startActivity(intent)
                finish()
            }else{
                firebaseuser = firebaseAuth.currentUser!!
                val username: String = firebaseuser.displayName.toString()
                var photourl: String = ""
                if(firebaseuser.photoUrl!=null){
                    photourl = firebaseuser.photoUrl.toString()
                }
                intent = Intent(this,HomeActivity::class.java)
                //start some activity
                startActivity(intent)
                finish()
            }
        },700)

    }
}
