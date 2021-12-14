package com.example.projectapplication.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapplication.R
import com.example.projectapplication.model.Product
import de.hdodenhof.circleimageview.CircleImageView

class DataAdapter (
    private var list: ArrayList<Product>,
    private var context: Context,
    private var listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener) : RecyclerView.Adapter<DataAdapter.DataViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        val productImageView: CircleImageView = itemView.findViewById(R.id.product_image_view)
        val sellerImageView: CircleImageView = itemView.findViewById(R.id.product_image_view)
        val sellerNameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        val productNameTextView: TextView = itemView.findViewById(R.id.product_name_text_view)
        val priceTextView: TextView = itemView.findViewById(R.id.price_text_view)
        val orderTextView: ImageView = itemView.findViewById(R.id.order_image_view)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]
        holder.productNameTextView.text = currentItem.title
        holder.sellerNameTextView.text = currentItem.username
        holder.priceTextView.text = currentItem.price_per_unit
        val images = currentItem.image
        if (images != null && images.size > 0){
            Log.d("DataClass", "#num_images: ${images.size}")
        }
    }

    override fun getItemCount() = list.size

    fun setData(newList: ArrayList<Product>){
        list = newList
    }

}