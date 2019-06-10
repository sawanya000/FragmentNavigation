package com.example.fragmentnavigation.Fragment

import android.content.Context

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fragmentnavigation.Database.DB



import com.example.fragmentnavigation.R
import kotlinx.android.synthetic.main.fragment_change_password.*


class ChangePasswordFragment() : Fragment() {
    lateinit var username :String
    lateinit var password :String
//    var myDb: SQLiteDatabase = SQLiteDatabase(context!!)
    var myDb = DB()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDatafromPreferences()
        startSQLiteDB()
        findDataUserInDB()
        setDataToView(getDatafromPreferences())
        setButtomClickListener()
    }

    private fun startSQLiteDB(){
        myDb.initDB(context!!)
    }

    private fun getDatafromPreferences():String{
        val sp = context!!.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        username = sp.getString("username","")
        return username
    }
    private fun setDataToView(username:String){
        edit_username.setText(username)
        //findDataUserInDB()
        editOldPass.setText(password)
        editNewPass.setText("")
        editConfPass.setText("")
        // edit_username.text= username as Editable
    }
    private fun setButtomClickListener(){

        button.setOnClickListener{
            checkEqualNewPass()
        }
    }
    private fun checkEqualNewPass(){
        var new_pass = editNewPass.getText().toString()
        var conf_pass = editConfPass.getText().toString()


        if (new_pass != "" && conf_pass != ""){
            if(new_pass == conf_pass){
                var g = myDb.getDB().editPassword(username,new_pass)

                findDataUserInDB()
                setDataToView(getDatafromPreferences())
                Toast.makeText(context!!,"SUCCESS", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(context!!,"Password not match", Toast.LENGTH_SHORT).show()

        }
        else{
            if(new_pass == "" ){
                Toast.makeText(context!!,"Pleses input New Password", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context!!,"Pleses input Confirm Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun findDataUserInDB(){
        //myDb.getDataUser(username)
        val m = myDb.getDB().getDataUser(username)

        if(m.moveToFirst()){
            password = m.getString(1)

        }


    }

}
