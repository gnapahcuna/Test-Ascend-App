package com.paiwaddev.paiwadpos.utils.module

import com.paiwaddev.paiwadpos.data.remote.ApiService
import com.paiwaddev.paiwadpos.data.remote.OkHttpBuilder
import com.paiwaddev.paiwadpos.data.remote.RetrofitBuilder
import com.paiwaddev.testascendapp.utils.network.NetworkUtil
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://ecommerce-product-app.herokuapp.com/"

val networkModule = module {
    single { OkHttpBuilder().build() }
    single<CallAdapter.Factory> { RxJava3CallAdapterFactory.create() }
    single<Converter.Factory> { GsonConverterFactory.create() }
    single { RetrofitBuilder(get(),get(),get()) }
    single<ApiService> { get<RetrofitBuilder>().build(BASE_URL) }

    //check internet
    factory { NetworkUtil(androidContext()) }
}