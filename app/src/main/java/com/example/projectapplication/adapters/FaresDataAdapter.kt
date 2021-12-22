package com.example.projectapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapplication.R
import com.example.projectapplication.model.Order
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FaresDataAdapter(
    private var list: ArrayList<Order>,
) : RecyclerView.Adapter<FaresDataAdapter.DataViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sellerNameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        val timeStampTextView: TextView = itemView.findViewById(R.id.timestamp_text_view)
        val detailButton: ImageView = itemView.findViewById(R.id.detail_button_image_view)
        val statusTextView: TextView = itemView.findViewById(R.id.order_type_text_view)
        val productNameTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
        val amountTextView: TextView = itemView.findViewById(R.id.amount_text_view)
        val priceTextView: TextView = itemView.findViewById(R.id.price_text_view)

        init {
            detailButton.setOnClickListener{detailCLick()}
        }

        private fun detailCLick(){
            if(descriptionTextView.visibility == View.GONE){
                descriptionTextView.visibility = View.VISIBLE
                detailButton.rotation= 90F
            } else {
                descriptionTextView.visibility = View.GONE
                detailButton.rotation= -90F
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_item_layout, parent, false)
        return DataViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]
        holder.sellerNameTextView.text = currentItem.username.replace("\"", "")
        holder.timeStampTextView.text = convertLongToTime(currentItem.creation_time)
        holder.statusTextView.text = currentItem.status.replace("\"","")
        holder.productNameTextView.text = currentItem.title.replace("\"", "")
        holder.amountTextView.text = "Amount: ${currentItem.units.replace("\"", "")}"
        holder.priceTextView.text = "Price: ${currentItem.price_per_unit.replace("\"", "")}"
        holder.descriptionTextView.text = currentItem.description.replace("\"", "")

    }

    override fun getItemCount() = list.size

    fun setData(newList: ArrayList<Order>){
        list = newList
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

}