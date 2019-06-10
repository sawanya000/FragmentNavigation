package com.example.fragmentnavigation.Fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.fragmentnavigation.Adapter.imageAdapter
import com.example.fragmentnavigation.BitmapToString
import com.example.fragmentnavigation.DataClass.storeImage
import com.example.fragmentnavigation.Database.DB
import com.example.fragmentnavigation.Database.SQLiteDatabase

import com.example.fragmentnavigation.R
import com.example.fragmentnavigation.resizeBitmap
import kotlinx.android.synthetic.main.fragment_list_image.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListImageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ListImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@SuppressLint("ValidFragment")
class ListImageFragment @SuppressLint("ValidFragment") constructor(f:FragmentTransaction) : Fragment(), imageAdapter.OnItemClickListener   {

    lateinit var myDb: SQLiteDatabase
    var time = ArrayList<String>()
    var objects = ArrayList<storeImage>()
    private val IMAGE_CAPTURE_CODE= 1001;
    private val PERMISSION_CODE = 1000;
    var image_uri:Uri? = null
    var recyclerView: RecyclerView? = null
    var arrBitmap = ArrayList<Bitmap>()
    var DB = DB()
    lateinit var myAdapter:imageAdapter
    var f:FragmentTransaction=f

    private  var swipBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))
    private lateinit var deleteIcon:Drawable
    override fun onItemClick(position: Int) {
        var m = objects.get(position)



        var fragmentManager:FragmentManager? = fragmentManager
        var fragmentTransaction:FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.addToBackStack("xyz");
        fragmentTransaction?.hide(this)
        fragmentTransaction?.replace(R.id.fragmentContainer,ShowlargeFragment(m.getlarge()))
        fragmentTransaction?.commit()
//        f.replace(R.id.fragmentContainer,ShowlargeFragment())
//        f.commit()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {



        return inflater.inflate(R.layout.fragment_list_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deleteIcon = ContextCompat.getDrawable(context!!,R.drawable.ic_delete)!!
        startSQLiteDB()
        showData()
        setButtomClickListenerToCamera()
        setRecyclerView()
        setRecyclerViewAdapter()
        setSwipDelete()
        //recyclerView!!.addOnItemTouchListener(onViewCreated(on))

        //myAdapter.setOnItemClickListener()


    }
    private fun setSwipDelete(){
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ){

            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                //var isInsert = myDb.insertData(BitmapToString(small),BitmapToString(large),currentDate.toString())
                (myAdapter as imageAdapter).removeImage(p0,DB.getDB(),objects)
                showData()

                //               // finish();
                //startActivity(getIntent());
                val myAdapter = imageAdapter(objects,time,context!!)
                recyclerView!!.setAdapter(myAdapter)


            }
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMarginVertical = (itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX > 0) {
//                    colorDrawableBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
//
                    swipBackground.setBounds(itemView.left,itemView.top,dX.toInt(),itemView.bottom)
                    deleteIcon.setBounds(itemView.left + iconMarginVertical, itemView.top + iconMarginVertical,
                        itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth, itemView.bottom - iconMarginVertical)
                } else {
//                    colorDrawableBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    swipBackground.setBounds(itemView.right+dX.toInt(),itemView.top,itemView.right,itemView.bottom)
                    deleteIcon.setBounds(itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth, itemView.top + iconMarginVertical,
                        itemView.right - iconMarginVertical, itemView.bottom - iconMarginVertical)
                    deleteIcon.level = 0

                }

                //colorDrawableBackground.draw(c)
                swipBackground.draw(c)


                c.save()
//
                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)

                deleteIcon.draw(c)

//
//                deleteIcon.draw(c)
//
                c.restore()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }
//        myAdapter = imageAdapter(objects,time,this)
//        recyclerView!!.setAdapter(myAdapter)


        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    private fun startSQLiteDB(){
        DB.initDB(context!!)
    }
    private fun setRecyclerView(){
        recyclerView = rcv_list_image as RecyclerView

        recyclerView!!.layoutManager = LinearLayoutManager(context!!) as RecyclerView.LayoutManager

    }

    private fun setRecyclerViewAdapter(){
        myAdapter = imageAdapter(objects,time,context!!)
        recyclerView!!.setAdapter(myAdapter)
        myAdapter.setOnItemClickListener(this)
    }
    private fun setButtomClickListenerToCamera(){
        buttonCamera.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(context!!,android.Manifest.permission.CAMERA)

                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(context!!,android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED    ){

                    val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

                    requestPermissions(permission,PERMISSION_CODE)

                }
                else{
                    //openCamera(image_uri,Bundle(),SherlockFragment.this.getActivity(),this,IMAGE_CAPTURE_CODE)
                    openCamera()

                }
            }
            else{
                openCamera()

            }
        }
    }
    private fun getDateTime():String{
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate
    }
    private fun createBitmap():Bitmap{
        var mBitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, image_uri)
        return mBitmap
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == Activity.RESULT_OK){


            val currentDate = getDateTime()


            time.add(currentDate.toString())

            var mBitmap = createBitmap()


            //arrBitmap.add(mBitmap)
            var small = resizeBitmap(mBitmap, 150, 150)
            var large = resizeBitmap(mBitmap, 600, 600)

            var Obj:storeImage = storeImage(BitmapToString(small) ,BitmapToString(large))
            objects.add(Obj)

            var isInsert = DB.getDB().insertData(BitmapToString(small),BitmapToString(large),currentDate.toString())

            setRecyclerViewAdapter()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    openCamera()
                }
                else{
                    Toast.makeText(context!!,"Permission denied", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
    private fun showData(){
        val res = DB.getDB().getAllData()


        time = ArrayList<String>()
        objects = ArrayList<storeImage>()
        if (res.getCount() != 0){

            while (res.moveToNext()){
                var Obj:storeImage = storeImage(res.getString(0) ,res.getString(1))
                objects.add(Obj)
                time.add(res.getString(2))

            }
        }
    }
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera")
        image_uri = context!!.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)

        // val path = image_uri.getPath()
        //val imagePath = get
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE)



    }

}
