package com.example.ourclass

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ourclass.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SignInActivity : AppCompatActivity() {

    private lateinit var username: String

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.SignInSignUpBtn.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.SignInCreateBtn.setOnClickListener {
            create()
        }
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
    }
    private fun create(){
        val pDialog = ProgressDialog(this)
        pDialog.setMessage("Loading..")
        pDialog.show()
        username = binding.SignInUsernameInputBox.text.toString().trim()
        val ref = database.getReference("User_Name_Data")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(User_Name_chacking(dataSnapshot)){
                    CreateData_Email()
                }else{
                    Toast.makeText(applicationContext,"Username Is Exist",Toast.LENGTH_LONG).show()
                    pDialog.dismiss()
                }
                // ...
            }

            private fun User_Name_chacking(dataSnapshot: DataSnapshot): Boolean {
                for (d in dataSnapshot.children) {
                    if(username == d.key){
                        return false
                    }else{
                        return true
                    }
                }
                return false
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("UserData", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        ref.addListenerForSingleValueEvent(postListener)

    }
    fun CreateData_Email(){
        val ref = database.getReference("User_Name_Data")
        val pDialog = ProgressDialog(this)
        val email = binding.SignInEmailInputBox.text.toString().trim()
        val password = binding.SignInPasswordInputBox.text.toString().trim()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                        val currentuser = FirebaseAuth.getInstance().currentUser
                        ref.child(username).setValue(currentuser!!.uid)
                            .addOnSuccessListener {
                                Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                            }
                        val intent = Intent(this,HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        pDialog.dismiss()
                    } else { // If sign in fails, display a message to the user.
                        pDialog.dismiss()
                        Toast.makeText(
                            this, "E-Mail is already exist.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // ...
                })
    }

}
