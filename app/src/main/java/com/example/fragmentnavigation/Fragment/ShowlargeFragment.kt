package com.example.fragmentnavigation.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ImageView

import com.example.fragmentnavigation.R
import com.example.fragmentnavigation.StringToBitmap
import kotlinx.android.synthetic.main.fragment_showlarge.*

import android.view.MotionEvent
import android.view.View.OnTouchListener

import android.view.View.inflate
import kotlinx.android.synthetic.main.activity_body.*





// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShowlargeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShowlargeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@SuppressLint("ValidFragment")
class ShowlargeFragment @SuppressLint("ValidFragment") constructor(imagelarge:String) : Fragment() {

    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var mScaleFactor = 1.0f
    private var mImageView: ImageView? = null

    var imagelarge:String = imagelarge
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_showlarge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("wwwwwwwwwwwwwww"+imageLarge)
//        var text:String = sp.getString("image","")
        imageLarge.setImageBitmap(StringToBitmap(imagelarge))
        mImageView = imageLarge


        mScaleGestureDetector = ScaleGestureDetector(context!!, ScaleListener())
//        view.setOnTouchListener(View.OnTouchListener(){
//            onTouchEvent(m:Mo)
//        })

    }



    override fun getView(): View? {
        return super.getView()
    }
    fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        mScaleGestureDetector?.onTouchEvent(motionEvent)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = Math.max(
                0.1f,
                Math.min(mScaleFactor, 10.0f)
            )
            mImageView?.setScaleX(mScaleFactor)
            mImageView?.setScaleY(mScaleFactor)
            return true
        }
    }



}
