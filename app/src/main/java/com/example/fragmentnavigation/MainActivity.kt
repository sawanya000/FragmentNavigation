package com.example.fragmentnavigation

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.Toast
import com.example.fragmentnavigation.Database.DB
import com.example.fragmentnavigation.Database.SQLiteDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()  {
    lateinit var username:String
    lateinit var password:String

    var myDb = DB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myDb.initDB(this)
        //val a= myDb.addUser("t","t")

        buttonLogin.setOnClickListener {

            username = editTextUsername.text.toString()
            password = editTextPassword.text.toString()
            val m = myDb.getDB().getDataUser(username)
            if(m.moveToFirst()){


                if (m.getString(1)!=password){
                    if (password ==""){
                        Toast.makeText(this,"Password input password ", Toast.LENGTH_SHORT).show()
                    }else
                    Toast.makeText(this,"Password not match ", Toast.LENGTH_SHORT).show()
                }
                else{
                    val sp = this.getSharedPreferences("user_data", Context.MODE_PRIVATE)
                    val editor = sp.edit()
                    editor.putString("username", m.getString(0))


                    editor.commit()
//
                    val intent = Intent(this,BodyActivity::class.java)
                    startActivity(intent)
                }
            }
            else {
                if(username != ""){
                    Toast.makeText(this,"Have not this username in database ", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"Please input data", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }


}
