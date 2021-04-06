package com.example.ourclass.fragment.firstlogfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.ourclass.R
import com.example.ourclass.SignInActivity
import com.example.ourclass.databinding.FragmentPage3fragmentBinding


class page3fragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPage3fragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_page3fragment, container, false)
        binding.Page3PreviousBtn.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(R.id.action_page3fragment_to_page2fragment)
        }
        binding.Page3DoneBtn.setOnClickListener { view: View ->
            activity?.finish()
            val intent = Intent(activity, SignInActivity::class.java)
            activity?.startActivity(intent)
        }
        return binding.root
    }


}
