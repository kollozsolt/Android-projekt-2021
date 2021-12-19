package com.example.projectapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.example.projectapplication.R
import com.synnapps.carouselview.CarouselView

class ProductDetailFragment : BaseFragment() {

    var sampleImages = intArrayOf(
        R.drawable.palinka,
        R.drawable.palinka,
        R.drawable.palinka,
        R.drawable.palinka,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_base, container, false)
        val viewOwn = inflater.inflate(R.layout.fragment_product_detail, container, false)
        val base: FrameLayout = view.findViewById(R.id.base_fragment)
        base.addView(viewOwn)
        val header: ConstraintLayout = view.findViewById(R.id.header_layout)
        val backButton: ImageView = header.findViewById(R.id.back_image_view)
        val productDetailTextView: TextView = header.findViewById(R.id.product_detail_text_view)
        val carouselView : CarouselView = viewOwn.findViewById(R.id.image_slider)

        carouselView.pageCount = sampleImages.size

        carouselView.setImageListener{ position, imageView ->
                imageView.setImageResource(sampleImages[position])
        }


        backButton.visibility = View.VISIBLE
        productDetailTextView.visibility = View.VISIBLE


        backButton.setOnClickListener{backButtonPress()}

        return view
    }

    private fun backButtonPress(){
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.popBackStack()
    }
}