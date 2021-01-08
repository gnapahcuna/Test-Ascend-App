package com.paiwaddev.paiwadpos.data.remote

import com.paiwaddev.testascendapp.data.model.Product
import com.paiwaddev.testascendapp.data.model.ProductContent
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {
    @GET("products")
    fun getProducts()
            : Observable<List<Product>>
    @GET("products/{productId}")
    fun getProductContent(@Path("productId") productID: Int?)
            : Observable<ProductContent>
}