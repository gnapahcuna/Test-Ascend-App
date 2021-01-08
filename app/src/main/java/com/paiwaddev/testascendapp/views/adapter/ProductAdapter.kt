package com.paiwaddev.testascendapp.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paiwaddev.testascendapp.R
import com.paiwaddev.testascendapp.data.model.Product
import com.paiwaddev.testascendapp.views.holder.ProductViewHolder
import java.text.DecimalFormat

class ProductAdapter(private val context: Context, private var products: List<Product>, private var view: ItemListener): RecyclerView.Adapter<ProductViewHolder>() {
    private lateinit var listener: ItemListener
    private lateinit var v: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        v = LayoutInflater.from(parent.context).inflate(R.layout.product_item_custom_view, parent, false)
        val holder = ProductViewHolder(v)
        return holder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val dec = DecimalFormat("#,###.##")

        if(products.get(position).isNewProduct)
            holder.tvNewProduct.text = "New"

        Glide.with(context)
            .load(products.get(position).imageProduct)
            .centerCrop()
            .error(R.drawable.unnamed)
            .placeholder(R.drawable.unnamed)
            .into(holder.imgViewProduct)

        holder.tvProductName.text = products.get(position).productName
        holder.tvProductPrice.text = dec.format(products.get(position).productPrice)

        v.setOnClickListener {
            this.listener.onItemClicked(products.get(position).ProductID)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    interface ItemListener {
        fun onItemClicked(productID: Int)
    }

    fun setListener() {
        this.listener = view;
    }
}