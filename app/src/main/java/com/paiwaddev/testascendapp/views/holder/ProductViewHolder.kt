package com.paiwaddev.testascendapp.views.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.testascendapp.R

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var imgViewProduct: ImageView = view.findViewById(R.id.img_view_product)
    var tvNewProduct: TextView = view.findViewById(R.id.tv_new_product)
    var tvProductName: TextView = view.findViewById(R.id.tv_product_name)
    var tvProductPrice: TextView = view.findViewById(R.id.tv_product_price)
}