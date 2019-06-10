package com.example.fragmentnavigation.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.fragmentnavigation.DataClass.storeImage
import com.example.fragmentnavigation.Database.SQLiteDatabase
import com.example.fragmentnavigation.R
import com.example.fragmentnavigation.StringToBitmap
import android.support.v4.app.Fragment

class imageAdapter (var items: ArrayList<storeImage>, var time:ArrayList<String>,val context: Context) : RecyclerView.Adapter<imageAdapter.imageHolder>() {

    private var removedPosition: Int = 0
    private var removedItem: String = ""
    private lateinit var mListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    override fun onBindViewHolder(p0: imageHolder, p1: Int) {
        p0.getImage?.setImageBitmap(StringToBitmap(items[p1].getsmall()))
//        p0.getImage?.setImageBitmap(items[p1])
        p0.getText?.text = time.get(p1)

        p0.itemView.setOnClickListener(View.OnClickListener {

                println("mmmmmmmmmmmmmmmmmmmmmmmmmmoooooooooooooooooooooookkkkkkkkkkkkkkkkkk")
                if(mListener != null){
                    var position = p1
                    if(position != RecyclerView.NO_POSITION){
                        mListener.onItemClick(position)

                    }
                }

        })
//        p0.itemView.setOnClickListener{
//
//            println("pppppp")
//
//
//
//            var intent = Intent(p0.itemView.context,LargeImage::class.java)
//
//            val sp = context.getSharedPreferences("large_image", Context.MODE_PRIVATE)
//            val editor = sp.edit()
//            editor.putString("image",items[p1].getlarge() )
//            editor.commit()
//
//            intent.putExtra("large_image","")
//
//
//            p0.itemView.context.startActivity(intent)
//        }
    }


    interface OnItemClickListener{
        fun onItemClick(position :Int)
    }
    override fun onCreateViewHolder(parentView: ViewGroup, option: Int): imageHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item,parentView,false)
        return imageHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
        //return 4
    }
    fun removeImage(p0: RecyclerView.ViewHolder, db:SQLiteDatabase, obj:ArrayList<storeImage>){
        removedItem = obj[p0.position].getsmall()
        removedPosition = p0.position
        println("pppppppppppppppppppooooooooooooooooooo   "+p0.position)

        db.daleteData(obj[p0.position].getsmall())



    }
    fun OnClickItemListener(){

    }
    inner class imageHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

        val getImage = itemView?.findViewById<ImageView>(R.id.imageView3)
        val getText = itemView?.findViewById<TextView>(R.id.textView5)

//        val m = itemView?.setOnClickListener(View.OnClickListener {
//            fun onClick(v:View){
//                if(mListener != null){
//                    var position = adapterPosition
//                    if(position != RecyclerView.NO_POSITION){
//                        mListener.onItemClick(position)
//
//                    }
//                }
//            }
//        })


    }

}