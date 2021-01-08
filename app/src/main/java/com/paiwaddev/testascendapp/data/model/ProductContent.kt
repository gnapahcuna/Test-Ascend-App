package com.paiwaddev.testascendapp.data.model

import com.google.gson.annotations.SerializedName

data class ProductContent (
    @SerializedName("id") val ProductID: Int,
    @SerializedName("title") val productName: String,
    @SerializedName("image") val imageProduct: String,
    @SerializedName("isNewProduct") val isNewProduct: Boolean,
    @SerializedName("content") val productContent: String,
    @SerializedName("price") val productPrice: Double
    )