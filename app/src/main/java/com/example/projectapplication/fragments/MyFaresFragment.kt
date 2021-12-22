package com.example.projectapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapplication.MyApplication
import com.example.projectapplication.R
import com.example.projectapplication.adapters.DataAdapter
import com.example.projectapplication.adapters.FaresDataAdapter
import com.example.projectapplication.manager.SharedPreferencesManager
import com.example.projectapplication.model.Order
import com.example.projectapplication.model.Product
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.OrderViewModel
import com.example.projectapplication.viewmodels.OrderViewModelFactory
import com.example.projectapplication.viewmodels.ProductViewModel
import com.example.projectapplication.viewmodels.ProductViewModelFactory

class MyFaresFragment : BaseFragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FaresDataAdapter
    lateinit var orderViewModel: OrderViewModel
    private lateinit var radioGroup: RadioGroup
    private lateinit var ongoingOrdersButton: RadioButton
    private lateinit var ongoingSalesButton: RadioButton
    private var name: String? = ""
    private lateinit var myOrders: ArrayList<Order>
    private lateinit var mySales: ArrayList<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrderViewModelFactory(Repository())
        orderViewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        val viewOwn = inflater.inflate(R.layout.fragment_my_fares, container, false)
        val base: FrameLayout = view.findViewById(R.id.base_fragment)
        base.addView(viewOwn)
        val header: ConstraintLayout = view.findViewById(R.id.header_layout)
        val faresText: TextView = header.findViewById(R.id.fares_text_view)
        val profilePicture: ImageView = header.findViewById(R.id.profile_image_view)
        val searchImageView: ImageView = header.findViewById(R.id.search_image_view)
        radioGroup = view.findViewById(R.id.radio_group)
        ongoingOrdersButton = radioGroup.findViewById(R.id.ongoing_orders)
        ongoingSalesButton = radioGroup.findViewById(R.id.ongoing_sales)
        name = MyApplication.sharedPreferences.getStringValue(SharedPreferencesManager.USER_NAME, "")
        Log.d("KAKA", name.toString())
        name = name?.replace("\"", "")
        recyclerView = viewOwn.findViewById(R.id.recycler_view)

        faresText.visibility = View.VISIBLE
        profilePicture.visibility = View.VISIBLE
        searchImageView.visibility = View.VISIBLE

//        mySales = orderViewModel.orders.value as ArrayList<Order>
//        myOrders = orderViewModel.orders.value as ArrayList<Order>
//        mySales!!.forEach{Log.d("KAKA", it.owner_username)}
//        myOrders!!.forEach{Log.d("KAKA", it.owner_username)}

        setupRecyclerView()
        orderViewModel.orders.observe(viewLifecycleOwner) {

            adapter.setData((orderViewModel.orders.value as ArrayList<Order>).filter { it.owner_username == name } as ArrayList<Order>)
            adapter.notifyDataSetChanged()
        }
        ongoingOrdersButton.setOnClickListener{ongoingClick()}
        ongoingSalesButton.setOnClickListener{ongoingClick()}


        profilePicture.setOnClickListener {
            val supportFragment: FragmentManager? = activity?.supportFragmentManager
            supportFragment?.beginTransaction()
                ?.replace(R.id.fragment_container, MyProfileFragment())?.addToBackStack(null)
                ?.commit()
        }

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun ongoingClick(){
        if(ongoingSalesButton.isChecked){
            Log.d("KAKA", "ONGOINGSALES")
            orderViewModel.orders.observe(viewLifecycleOwner) {
                adapter.setData((orderViewModel.orders.value as ArrayList<Order>).filter { it.owner_username == name } as ArrayList<Order>)
                adapter.notifyDataSetChanged()
            }
        } else{
            Log.d("KAKA", "ONGOINGORDERS")
            orderViewModel.orders.observe(viewLifecycleOwner) {
                adapter.setData((orderViewModel.orders.value as ArrayList<Order>).filter { it.username == name } as ArrayList<Order>)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = FaresDataAdapter(ArrayList<Order>())
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
}