package com.example.ourclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ourclass.databinding.ActivityHomeBinding
import com.example.ourclass.fragment.AccountFragment
import com.example.ourclass.fragment.CartFragment
import com.example.ourclass.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase


class HomeActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var binding: ActivityHomeBinding
    private var lastFragment: Int = 0
    private var FragmentList: ArrayList<Fragment> = ArrayList()
    var managers = supportFragmentManager.beginTransaction()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {menuItem ->
        when(menuItem.itemId){
            R.id.Course_Home_menu->{
                Changeing_Fragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.Course_Cart_menu->{
                Changeing_Fragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.Course_Account_menu->{
                Changeing_Fragment(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)

        //managers.add(R.id.Fragment_Holder,HomeFragment()).addToBackStack(null).commit()

        lastFragment = 0


       FragmentList.apply {
           add(HomeFragment())
           add(CartFragment())
           add(AccountFragment())
           add(HomeFragment())
       }

        Changeing_Fragment(3)

        binding.HomeBottomNavigationBar.menu.findItem(R.id.Course_Home_menu).setChecked(true)
        binding.HomeBottomNavigationBar.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        database = FirebaseDatabase.getInstance()
    }

    private fun Changeing_Fragment(type: Int){
        var manager = supportFragmentManager.beginTransaction()
        when{
            FragmentList[type].isAdded ->{
               if(lastFragment!=type){
                   manager.hide(FragmentList[lastFragment]).show(FragmentList[type]).commit()
                   lastFragment=type
               }
            }
            else->{
                if(type==3){
                    manager.add(R.id.Fragment_Holder,FragmentList[0]).commit()
                }else {
                    manager.hide(FragmentList[lastFragment])
                        .add(R.id.Fragment_Holder, FragmentList[type]).commit()
                    lastFragment = type
                }
            }
        }
    }
}
