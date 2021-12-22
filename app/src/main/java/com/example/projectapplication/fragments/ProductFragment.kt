package com.example.projectapplication.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapplication.MyApplication
import com.example.projectapplication.R
import com.example.projectapplication.adapters.DataAdapter
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.Product
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.coroutines.launch

class ProductFragment : BaseFragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener {

    lateinit var productViewModel: ProductViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter
    private var product: Product? = Product()
    private lateinit var filterIcon: ImageView
    private var myMarket: View? = null


    private lateinit var deleteProductViewModel: DeleteProductViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ProductViewModelFactory(Repository())
        val factoryDelete = DeleteProductViewModelFactory(requireContext(), Repository())
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)
        deleteProductViewModel = ViewModelProvider(this, factoryDelete).get(DeleteProductViewModel::class.java)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        val viewOwn = inflater.inflate(R.layout.fragment_list, container, false)
        val base: FrameLayout = view.findViewById(R.id.base_fragment)
        base.addView(viewOwn)

        recyclerView = viewOwn.findViewById(R.id.recycler_view)
        setupRecyclerView()
        productViewModel.products.observe(viewLifecycleOwner) {
            adapter.setData(productViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }
        val myName = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.USER_NAME, "")
        val header: ConstraintLayout = view.findViewById(R.id.header_layout)
        val profilePicture: ImageView = header.findViewById(R.id.profile_image_view)
        val logo: ImageView = header.findViewById(R.id.logo_image_view)
        val floatingButtonAdd: FloatingActionButton = view.findViewById(R.id.add_floating_button)
        filterIcon = header.findViewById(R.id.filter_image_view)
        val searchIcon: ImageView = header.findViewById(R.id.search_image_view)
        val navBase: ChipNavigationBar? = activity?.findViewById(R.id.bottom_navigation)
        myMarket= navBase?.findViewById(R.id.ic_market)
        profilePicture.visibility = View.VISIBLE
        logo.visibility = View.VISIBLE;
        if (myMarket?.isSelected == false) {
            filterIcon.visibility = View.VISIBLE
            searchIcon.visibility = View.VISIBLE
        } else{
            floatingButtonAdd.visibility = View.VISIBLE
            floatingButtonAdd.setOnClickListener{floatingAddClick()}
        }
        filterIcon.setOnClickListener { filterClick() }


        profilePicture.setOnClickListener {
            if (myName != null) {
                MyApplication.sharedPreferences.putStringValue(
                    SharedPreferencesManager.USER_INFO_NAME,
                    myName
                )
            }
            val supportFragment: FragmentManager? = activity?.supportFragmentManager
            supportFragment?.beginTransaction()
                ?.replace(R.id.fragment_container, MyProfileFragment())?.addToBackStack(null)
                ?.commit()
        }

        return view
    }

    private fun setupRecyclerView() {
        adapter = DataAdapter(ArrayList<Product>(), this.requireContext(), this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
        product = productViewModel.products.value?.get(position)
        sharedViewModel.setProduct(product)
        Log.d("KAKA", productViewModel.products.value?.get(position)?.title.toString())
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container, ProductDetailFragment())?.addToBackStack(null)
            ?.commit()
    }

    override fun onItemLongClick(position: Int) {
        if (myMarket?.isSelected == true) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    Log.d("KAKA", "torles")
                    val product_id : String= productViewModel.products.value?.get(position)?.product_id!!.toString()
                    deleteProductViewModel.product_id.let {
                        if(it!=null) {
                            it.value = product_id
                        }
                    }
                    lifecycleScope.launch {
                        deleteProductViewModel.deleteProduct()
                    }
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());

                    val supportFragment: FragmentManager? = activity?.supportFragmentManager
                    supportFragment?.popBackStack()
                    supportFragment?.beginTransaction()
                        ?.replace(R.id.fragment_container, ProductFragment())?.addToBackStack(null)
                        ?.commit()
                }
                .setNegativeButton("No"){ dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    private fun filterClick() {
        filterIcon.setColorFilter(getResources().getColor(R.color.B_turquoise))
        val mDialogView: View = LayoutInflater.from(requireContext()).inflate(R.layout.popup, null)
        val mBuilder = AlertDialog.Builder(requireContext(), R.style.my_dialog).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        val sendButton: Button = mDialogView.findViewById(R.id.filtering_button)
        val removeButton: Button = mDialogView.findViewById(R.id.remove_filters_button)

        val filter =
            MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.FILTER, "")
        val sort = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.SORT, "")
        val limit =
            MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.LIMIT, "20")

        if (filter == "{}" && sort == "{}" && limit == "20") {
            removeButton.visibility = View.GONE
        } else {
            removeButton.setOnClickListener { removeClick(mAlertDialog) }
        }
        sendButton.setOnClickListener { sendFilterClick(mAlertDialog, mDialogView) }
    }

    private fun removeClick(mAlertDialog: AlertDialog) {
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.FILTER, "{}")
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.SORT, "{}")
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.LIMIT, "20")
        mAlertDialog.cancel()
        filterIcon.setColorFilter(getResources().getColor(R.color.white))
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.popBackStack()
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container, ProductFragment())?.addToBackStack(null)?.commit()
    }

    private fun sendFilterClick(mAlertDialog: AlertDialog, mDialogView: View) {
        val ratingText: TextView = mDialogView.findViewById(R.id.rating_text_edit)
        val usernameText: TextView = mDialogView.findViewById(R.id.username_text_edit)
        val titleText: TextView = mDialogView.findViewById(R.id.title_text_edit)
        val activeRadioGroup: RadioGroup = mDialogView.findViewById(R.id.active_radio_group)
        val active: RadioButton = activeRadioGroup.findViewById(R.id.active_true)
        val inactive: RadioButton = activeRadioGroup.findViewById(R.id.active_false)
        val titleRadioGroup: RadioGroup = mDialogView.findViewById(R.id.title_radio_group)
        val titleAsc: RadioButton = titleRadioGroup.findViewById(R.id.title_asc)
        val titleDesc: RadioButton = titleRadioGroup.findViewById(R.id.title_desc)
        val ratingRadioGroup: RadioGroup = mDialogView.findViewById(R.id.rating_radio_group)
        val ratingAsc: RadioButton = ratingRadioGroup.findViewById(R.id.rating_asc)
        val ratingDesc: RadioButton = ratingRadioGroup.findViewById(R.id.rating_desc)
        val limitText: TextView = mDialogView.findViewById(R.id.limit_text_edit)

        var actVal = "";
        if (active.isChecked) actVal = "true"
        if (inactive.isChecked) actVal = "false"
        var filterText = "{"
        if (!usernameText.text.isEmpty()) filterText += "\"username\":\"${usernameText.text}\","
        if (!titleText.text.isEmpty()) filterText += "\"title\":\"${titleText.text}\","
        if (!ratingText.text.isEmpty()) filterText += "\"rating\":\"${ratingText.text}\","
        if (!actVal.isEmpty()) filterText += "\"is_active\":\"$actVal\","
        if (filterText.last() == ',') filterText = filterText.dropLast(1) + '}'
        else filterText += '}'

        var sortText = "{"
        var titleSort = ""
        if (titleAsc.isChecked) titleSort = "1"
        if (titleDesc.isChecked) titleSort = "-1"
        var ratingSort = ""
        if (ratingAsc.isChecked) ratingSort = "1"
        if (ratingDesc.isChecked) ratingSort = "-1"
        if (!titleSort.isEmpty()) sortText += "\"title\":\"$titleSort\","
        if (!ratingSort.isEmpty()) sortText += "\"rating\":\"$ratingSort\","
        if (sortText.last() == ',') sortText = sortText.dropLast(1) + '}'
        else sortText += '}'

        var limit = ""
        if (!limitText.text.isEmpty()) limit = limitText.text.toString()
        else limit = "20"

        Log.d("KAKA", "$filterText \n $sortText \n$limit")
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.FILTER, filterText)
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.SORT, sortText)
        MyApplication.sharedPreferences.putStringValue(SharedPreferencesManager.LIMIT, limit)
        mAlertDialog.cancel()
        filterIcon.setColorFilter(getResources().getColor(R.color.white))

        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.popBackStack()
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container, ProductFragment())?.addToBackStack(null)?.commit()
    }

    private fun floatingAddClick(){
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container, AddProductFragment())?.addToBackStack(null)
            ?.commit()
    }

}