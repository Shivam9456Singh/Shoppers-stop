package com.martinestudio.api

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.martinestudio.shoppersstop.Home
import com.martinestudio.shoppersstop.MyData
import com.martinestudio.shoppersstop.R
import com.squareup.picasso.Picasso

class MyAdapter(private val context: Home, private val productArrayList: List<MyData.Product>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var myListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return MyViewHolder(itemView, myListener)
    }

    override fun getItemCount(): Int {
        return productArrayList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productArrayList[position]
        holder.title.text = currentItem.title
        holder.price.text = "Price: $" + currentItem.price.toString()
        holder.brand.text = "Brand: " + currentItem.brand
        holder.rating.text = "Ratings: " + currentItem.rating.toString()

        // Load image using Picasso library
        Picasso.get().load(currentItem.thumbnail).into(holder.image)

        // Handle item click
        holder.itemView.setOnClickListener {
            myListener?.onItemClick(position)
        }
    }

    class MyViewHolder(itemView: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.productName)
        val image: ShapeableImageView = itemView.findViewById(R.id.productImage)
        val price: TextView = itemView.findViewById(R.id.productPrice)
        val brand: TextView = itemView.findViewById(R.id.BrandName)
        val rating: TextView = itemView.findViewById(R.id.productRating)

        init {
            listener?.let {
                itemView.setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }
            }
        }
    }
}
