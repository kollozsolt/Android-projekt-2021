package com.example.projectapplication.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.media.Image
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayoutStates
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectapplication.MyApplication
import com.example.projectapplication.R
import com.example.projectapplication.databinding.FragmentAddProductBinding
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.Product
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.*
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAmount

class AddProductFragment : BaseFragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var header: ConstraintLayout
    private lateinit var backBtn: ImageView
    private lateinit var createText: TextView
    private lateinit var base: FrameLayout
    private lateinit var priceTypeText: AutoCompleteTextView
    private lateinit var amountTypeText: AutoCompleteTextView
    private lateinit var priceType: Array<String>
    private lateinit var amountType: Array<String>
    private lateinit var switchButton: SwitchCompat
    private lateinit var activeTextView: TextView
    private lateinit var previewButton: Button
    private lateinit var launchButton: Button
    private lateinit var titleText: TextView
    private lateinit var priceText: TextView
    private lateinit var availableAmountText: TextView
    private lateinit var descriptionText: TextView

    private lateinit var addProductViewModel: AddProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = AddProductViewModelFactory(this.requireContext(), Repository())
        addProductViewModel = ViewModelProvider(this, factory).get(AddProductViewModel::class.java)
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_base, container, false)
        val viewOwn = inflater.inflate(R.layout.fragment_add_product, container, false)
        header = view.findViewById(R.id.header_layout)
        backBtn = header.findViewById(R.id.back_image_view)
        createText = header.findViewById(R.id.create_text_view)
        base = view.findViewById(R.id.base_fragment)
        priceTypeText = viewOwn.findViewById(R.id.price_type_edit_text)
        amountTypeText= viewOwn.findViewById(R.id.amount_type_edit_text)
        priceType = resources.getStringArray(R.array.price_type_arr)
        amountType = resources.getStringArray(R.array.amount_type_arr)
        switchButton = viewOwn.findViewById(R.id.active_switch)
        activeTextView = viewOwn.findViewById(R.id.is_active_text_view)
        previewButton = viewOwn.findViewById(R.id.preview_button)
        launchButton = viewOwn.findViewById(R.id.launch_button)
        titleText = viewOwn.findViewById(R.id.title_text_edit)
        priceText = viewOwn.findViewById(R.id.price_amount_edit_text)
        availableAmountText = viewOwn.findViewById(R.id.available_amount_edit_text)
        descriptionText = viewOwn.findViewById(R.id.description_text_edit)

        //Setting the header element's visibility
        backBtn.visibility = View.VISIBLE
        createText.visibility = View.VISIBLE
        backBtn.setOnClickListener{backClick()}

        ///Setting adapters
        val priceAdapter : ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, priceType)
        val amountAdapter : ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, amountType)
        priceTypeText.setText(priceType[0])
        priceTypeText.setAdapter(priceAdapter)

        amountTypeText.setText(amountType[0])
        amountTypeText.setAdapter(amountAdapter)

        ///Buttons listeners
        switchButton.setOnClickListener{isChecked()}
        previewButton.setOnClickListener{previewClicked()}
        launchButton.setOnClickListener{launchClick()}

        base.addView(viewOwn)

        return view
    }

    private fun isChecked(){
        if(switchButton.isChecked) activeTextView.text = "Active"
        else activeTextView.text = "Inactive"
    }

    private fun backClick(){
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.popBackStack()
    }

    private fun launchClick(){
        if (isFilled()){
            addProductViewModel.product.value.let {
                if(it!=null){
                    it.title = titleText.text.toString()
                    it.description = descriptionText.text.toString()
                    it.price_per_unit = priceText.text.toString()
                    it.units = availableAmountText.text.toString()
                    it.is_active = switchButton.isChecked
                    it.amount_type = amountTypeText.text.toString()
                    it.price_type = priceTypeText.text.toString()
                    it.creation_time = System.currentTimeMillis()
                    it.rating = 5.0
                }
            }
            lifecycleScope.launch {
                addProductViewModel.addProduct()
            }
            val supportFragment: FragmentManager? = activity?.supportFragmentManager
            supportFragment?.popBackStack()
        }
    }

    private fun previewClicked(){
        if(isFilled()) {
            val product = Product()
            product.title = titleText.text.toString()
            product.amount_type = amountTypeText.text.toString()
            product.units = availableAmountText.text.toString()
            product.price_type = priceTypeText.text.toString()
            product.price_per_unit = priceText.text.toString()
            product.description = descriptionText.text.toString()
            product.is_active = switchButton.isChecked
            product.creation_time = System.currentTimeMillis()
            product.username = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.USER_NAME, "").toString()
            sharedViewModel.setProduct(product)
            val supportFragment: FragmentManager? = activity?.supportFragmentManager
            supportFragment?.beginTransaction()
                ?.replace(R.id.fragment_container, ProductDetailFragment())?.addToBackStack(null)?.commit()

        }
    }

    private fun isFilled():Boolean{
        var correct = true
        if(titleText.text.isEmpty()) {
            titleText.setError("Your fair needs to have a title")
            correct = false
        }
        if (priceText.text.isEmpty()){
            priceText.setError("Give your fare a fair price")
            correct = false
        }
        if(availableAmountText.text.isEmpty()){
            availableAmountText.setError("Select an amount")
            correct = false
        }
        if (descriptionText.text.isEmpty()){
            descriptionText.setError("Describe your product, like its origin, made for...")
            correct = false
        }
        return correct
    }
}