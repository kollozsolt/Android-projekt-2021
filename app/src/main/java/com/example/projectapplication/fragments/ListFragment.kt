package com.example.projectapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapplication.MyApplication
import com.example.projectapplication.R
import com.example.projectapplication.adapters.DataAdapter
import com.example.projectapplication.model.Image
import com.example.projectapplication.model.Product
import com.example.projectapplication.repository.Repository
import com.example.projectapplication.viewmodels.ListViewModel
import com.example.projectapplication.viewmodels.ListViewModelFactory

class ListFragment : ProductBaseFragment(), DataAdapter.OnItemClickListener, DataAdapter.OnItemLongClickListener{

    lateinit var listViewModel: ListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory(Repository())
        listViewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_product_base, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.visibility = View.VISIBLE
        setupRecyclerView()
        listViewModel.products.observe(viewLifecycleOwner){
            adapter.setData(listViewModel.products.value as ArrayList<Product>)
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

        profilePicture.setOnClickListener{showMyProfile()}

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
//        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int) {
//        TODO("Not yet implemented")
    }

    private fun showMyProfile(){
        val supportFragment: FragmentManager? = activity?.supportFragmentManager
        supportFragment?.beginTransaction()
            ?.replace(R.id.fragment_container, MyProfileFragment())?.addToBackStack(null)?.commit()
    }
}