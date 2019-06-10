package com.example.fragmentnavigation

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import com.example.fragmentnavigation.Adapter.imageAdapter
import com.example.fragmentnavigation.Database.DB
import com.example.fragmentnavigation.Fragment.ChangePasswordFragment
import com.example.fragmentnavigation.Fragment.ListImageFragment
import com.example.fragmentnavigation.Fragment.LoginFragment

import kotlinx.android.synthetic.main.activity_body.*

class BodyActivity : AppCompatActivity(){
    lateinit var   fragmentTransaction:FragmentTransaction

    var myDb = DB()
    private  val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home -> {
                println("home pressed")
                //replaceFragment(LoginFragment())
                LogOut()
                return@OnNavigationItemSelectedListener true
            }
            R.id.map -> {
                println("map pressed")
                replaceFragment(ListImageFragment(fragmentTransaction))
                return@OnNavigationItemSelectedListener true
            }
            R.id.cart -> {
                println("cart pressed")
                replaceFragment(ChangePasswordFragment())
                return@OnNavigationItemSelectedListener true
            }


        };false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentTransaction = supportFragmentManager.beginTransaction()
        setContentView(R.layout.activity_body)
//        myDb.initDB(this)
//        myDb.getDB().getDataUser("t")
        replaceFragment(ListImageFragment(fragmentTransaction))

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    private fun replaceFragment(fragment: Fragment){
        fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()

    }
    private fun LogOut(){
        val alertdialog = AlertDialog.Builder(this)
        alertdialog.setTitle("LogOut")
        alertdialog.setMessage("Are you sure you Want to LogOut the app???")
        alertdialog.setPositiveButton(
            "yes"
        ) { dialog, which -> super.onBackPressed() }

        alertdialog.setNegativeButton(
            "No"
        ) { dialog, which -> dialog.cancel() }

        val alert = alertdialog.create()
        alertdialog.show()

    }
    override fun onBackPressed() {

        val alertdialog = AlertDialog.Builder(this)
        alertdialog.setTitle("LogOut")
        alertdialog.setMessage("Are you sure you Want to LogOut the app???")
        alertdialog.setPositiveButton(
            "yes"
        ) { dialog, which -> super.onBackPressed() }

        alertdialog.setNegativeButton(
            "No"
        ) { dialog, which -> dialog.cancel() }

        val alert = alertdialog.create()
        alertdialog.show()

    }

}
