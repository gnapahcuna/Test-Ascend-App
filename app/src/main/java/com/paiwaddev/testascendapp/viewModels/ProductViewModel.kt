package com.paiwaddev.testascendapp.viewModels

import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paiwaddev.paiwadpos.data.respository.ProductRepository
import com.paiwaddev.testascendapp.data.model.Product
import com.paiwaddev.testascendapp.data.model.ProductContent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductViewModel(private val repository: ProductRepository): ViewModel() {

    private var productList = MutableLiveData<List<Product>>()
    private var product = MutableLiveData<ProductContent>()
    private var errorMsg = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()

    fun errorMessage(): LiveData<String> = errorMsg

    fun getProducts(progressBar: View, layout_main: LinearLayout): LiveData<List<Product>>{
        progressBar.visibility = View.VISIBLE

        val disposable = repository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({products ->
                progressBar.visibility = View.GONE
                layout_main.visibility = View.VISIBLE
                productList.value = products
            },{error ->
                progressBar.visibility = View.GONE
                errorMsg.value = error.localizedMessage
            })
        compositeDisposable.add(disposable)

        return productList
    }

    fun getProductContent(productID: Int,dialog: View): LiveData<ProductContent>{
        dialog.visibility = View.VISIBLE

        val disposable = repository.getProductContent(productID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({products ->
                dialog.visibility = View.GONE
                product.value = products
            },{error ->
                dialog.visibility = View.GONE
                errorMsg.value = error.localizedMessage
            })
        compositeDisposable.add(disposable)

        return product
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }



}