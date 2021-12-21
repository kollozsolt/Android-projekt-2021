package com.example.projectapplication.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.projectapplication.MyApplication
import com.example.projectapplication.R
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.Image
import com.example.projectapplication.model.Product
import com.example.projectapplication.viewmodels.SharedViewModel
import com.synnapps.carouselview.CarouselView
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class ProductDetailFragment : BaseFragment() {

    var sampleImages = intArrayOf(
        R.drawable.palinka,
        R.drawable.palinka,
        R.drawable.palinka,
        R.drawable.palinka,
    )
    private var product: Product? = Product()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
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
        val profileImage: CircleImageView = header.findViewById(R.id.profile_image_view)
        val carouselView : CarouselView = viewOwn.findViewById(R.id.image_slider)
        val nameTextView: TextView = viewOwn.findViewById(R.id.name_text_view)
        val dateTextView: TextView = viewOwn.findViewById(R.id.date_text_view)
        val titleTextView: TextView = viewOwn.findViewById(R.id.title_text_view)
        val priceTextView: TextView = viewOwn.findViewById(R.id.price_text_view)
        val availableTextView: TextView = viewOwn.findViewById(R.id.available_text_view)
        val descriptionTextView: TextView = viewOwn.findViewById(R.id.description_text_view)
        val activeImageView: ImageView = viewOwn.findViewById(R.id.active_image_view)
        val orderImage: ImageView = view.findViewById(R.id.order_image_view)
        val emailImage: ImageView = view.findViewById(R.id.email_image_view)
        val phoneImage: ImageView = view.findViewById(R.id.call_image_view)
        val name = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.USER_NAME, "")
        val editButton: ImageView = view.findViewById(R.id.edit_image_view)

        carouselView.pageCount = sampleImages.size

        carouselView.setImageListener{ position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }
        product = sharedViewModel.getProduct()
        nameTextView.text = product?.username?.replace("\"", "")
        dateTextView.text = product?.creation_time?.let { convertLongToTime(it) }
        titleTextView.text = product?.title?.replace("\"", "")
        priceTextView.text = "${product?.price_per_unit?.replace("\"", "")} ${product?.price_type?.replace("\"", "")}/ ${product?.amount_type?.replace("\"", "")}"
        availableTextView.text = "Available amount: ${product?.units?.replace("\"", "")} ${product?.amount_type?.replace("\"", "")}"
        descriptionTextView.text = product?.description?.replace("\"", "")

        if( !product?.is_active!!) {
            activeImageView.setImageResource(R.drawable.ic_inacive)
            orderImage.setImageResource(R.drawable.ic_call)
            emailImage.visibility = View.GONE
            phoneImage.visibility = View.GONE
        }

        if(product?.username == name){
            editButton.visibility = View.VISIBLE
        }

        profileImage.visibility = View.VISIBLE
        backButton.visibility = View.VISIBLE
        productDetailTextView.visibility = View.VISIBLE

        nameTextView.setOnClickListener{userInfoClick(nameTextView.text.toString())}

        backButton.setOnClickListener{backButtonPress()}

        return view
    }

    private fun userInfoClick(name: String){
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.USER_INFO_NAME, name)
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container, MyProfileFragment())?.addToBackStack(null)?.commit()
    }

    private fun backButtonPress(){
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.popBackStack()
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
}