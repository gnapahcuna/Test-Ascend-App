package com.paiwaddev.paiwadpos.data.respository

import com.paiwaddev.testascendapp.data.model.Product
import com.paiwaddev.testascendapp.data.model.ProductContent
import io.reactivex.rxjava3.core.Observable

interface ProductRepository {
    fun getProducts(): Observable<List<Product>>
    fun getProductContent(q: Int): Observable<ProductContent>
}