package com.example.ourclass.fragment.firstlogfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.ourclass.R
import com.example.ourclass.databinding.FragmentPage1fragmentBinding


class page1fragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPage1fragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_page1fragment, container, false)
        binding.Page1NextBtn.setOnClickListener {view: View ->
            Navigation.findNavController(view).navigate(R.id.action_page1fragment_to_page2fragment)
        }
        return binding.root
    }

}
