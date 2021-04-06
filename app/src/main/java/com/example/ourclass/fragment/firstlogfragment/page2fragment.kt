package com.example.ourclass.fragment.firstlogfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.ourclass.R
import com.example.ourclass.databinding.FragmentPage2fragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class page2fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPage2fragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_page2fragment, container, false)
        binding.Page2NextBtn.setOnClickListener {view: View ->
            Navigation.findNavController(view).navigate(R.id.action_page2fragment_to_page3fragment)
        }
        binding.Page2PreviousBtn.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(R.id.action_page2fragment_to_page1fragment)
        }
        return binding.root
    }


}
