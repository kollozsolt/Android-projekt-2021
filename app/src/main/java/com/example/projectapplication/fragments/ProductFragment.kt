package com.example.projectapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapplication.R
import com.example.projectapplication.adapters.DataAdapter
import com.example.projectapplication.model.Product
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.ProductViewModel
import com.example.projectapplication.viewmodels.ProductViewModelFactory

class ProductFragment : BaseFragment(), DataAdapter.OnItemClickListener, DataAdapter.OnItemLongClickListener{

    lateinit var productViewModel: ProductViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ProductViewModelFactory(Repository())
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_base, container, false)
        val viewOwn = inflater.inflate(R.layout.fragment_list, container, false)
        val base : FrameLayout = view.findViewById(R.id.base_fragment)
        base.addView(viewOwn)

        recyclerView = viewOwn.findViewById(R.id.recycler_view)
        setupRecyclerView()
        productViewModel.products.observe(viewLifecycleOwner){
            adapter.setData(productViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }
        val header: ConstraintLayout = view.findViewById(R.id.header_layout)
        val profilePicture: ImageView = header.findViewById(R.id.profile_image_view)
        val logo: ImageView = header.findViewById(R.id.logo_image_view)
        val filterIcon: ImageView = header.findViewById(R.id.filter_image_view)
        val searchIcon: ImageView = header.findViewById(R.id.search_image_view)
        profilePicture.visibility = View.VISIBLE;
        logo.visibility = View.VISIBLE;
        filterIcon.visibility = View.VISIBLE;
        searchIcon.visibility = View.VISIBLE;

        profilePicture.setOnClickListener{
            val supportFragment: FragmentManager? = activity?.supportFragmentManager
            supportFragment?.beginTransaction()
                ?.replace(R.id.fragment_container, MyProfileFragment())?.addToBackStack(null)?.commit()
        }

        return view
    }

    private fun setupRecyclerView(){
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
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container, ProductDetailFragment())?.addToBackStack(null)?.commit()
    }

    override fun onItemLongClick(position: Int) {
//        TODO("Not yet implemented")
    }


}