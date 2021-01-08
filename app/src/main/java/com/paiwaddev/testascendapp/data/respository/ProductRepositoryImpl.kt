package com.paiwaddev.paiwadpos.data.respository

import com.paiwaddev.paiwadpos.data.remote.ApiService
import com.paiwaddev.testascendapp.data.model.Product
import com.paiwaddev.testascendapp.data.model.ProductContent
import io.reactivex.rxjava3.core.Observable

class ProductRepositoryImpl(private val apiService: ApiService): ProductRepository {
    override fun getProducts(): Observable<List<Product>> = apiService.getProducts().map { it }

    override fun getProductContent(q: Int): Observable<ProductContent> =
        apiService.getProductContent(q).map { it }

}