package com.example.ourclass

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ourclass.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth



class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySignUpBinding>(this,R.layout.activity_sign_up)
        //inisialisasi firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //onclick
        binding.SignUpLoginBtn.setOnClickListener {
            Login_Process()
        }
    }

    private fun Login_Process(){
        val pDialog = ProgressDialog(this)
        val email = binding.SignUpUserNameInputBox.text.toString().trim { it <= ' ' }
        val password = binding.SignUpPasswordInputBox.text.toString().trim { it <= ' ' }
        pDialog.setMessage("In Proccess.... ")
        pDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                        pDialog.dismiss()
                        val intent = Intent(this,HomeActivity::class.java)
                        startActivity(intent)
                        //val user: FirebaseUser = firebaseAuth.getCurrentUser()!!
                    } else { // If sign in fails, display a message to the user.
                        pDialog.dismiss()
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // ...
                })
    }
}
