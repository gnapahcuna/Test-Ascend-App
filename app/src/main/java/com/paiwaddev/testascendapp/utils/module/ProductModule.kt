package com.paiwaddev.paiwadpos.utils.module

import com.paiwaddev.paiwadpos.data.respository.ProductRepository
import com.paiwaddev.paiwadpos.data.respository.ProductRepositoryImpl
import com.paiwaddev.testascendapp.data.model.Product
import com.paiwaddev.testascendapp.viewModels.ProductViewModel
import com.paiwaddev.testascendapp.views.SpacesItemDecoration
import com.paiwaddev.testascendapp.views.adapter.ProductAdapter
import com.paiwaddev.testascendapp.views.holder.ProductViewHolder
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productModule = module {
    single { (products: List<Product>,view: ProductAdapter.ItemListener) -> ProductAdapter(androidContext(), products,view) }

    factory<ProductRepository> { ProductRepositoryImpl(get()) }
    factory { ProductViewHolder(get()) }
    viewModel { ProductViewModel(get()) }
}