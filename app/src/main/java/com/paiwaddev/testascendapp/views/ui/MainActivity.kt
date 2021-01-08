package com.paiwaddev.testascendapp.views.ui

import android.app.ProgressDialog
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.testascendapp.R
import com.paiwaddev.testascendapp.utils.network.ConnectionType
import com.paiwaddev.testascendapp.utils.network.NetworkUtil
import com.paiwaddev.testascendapp.viewModels.ProductViewModel
import com.paiwaddev.testascendapp.views.SpacesItemDecoration
import com.paiwaddev.testascendapp.views.adapter.ProductAdapter
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), ProductAdapter.ItemListener {
    private val viewModel: ProductViewModel by viewModel()
    private val networkMonitor: NetworkUtil by inject()

    private lateinit var rcvProductList: RecyclerView
    private lateinit var dialogLoading: View
    private lateinit var layout_error: View
    private lateinit var layout_main: LinearLayout
    private lateinit var itemDecor: SpacesItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false);
        supportActionBar?.setDisplayHomeAsUpEnabled(false);

        bindingView()

        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {

                        layout_error.visibility = View.GONE

                        viewModel.getProducts(dialogLoading, layout_main).observe(this, { products ->

                            val adapter: ProductAdapter = get { parametersOf(products, this) }
                            adapter.setListener()
                            rcvProductList.adapter = adapter

                        })

                        viewModel.errorMessage().observe(this, { errorMsg ->

                            layout_error.visibility = View.VISIBLE

                            val tv = layout_error.findViewById<TextView>(R.id.tvError)
                            tv.text = errorMsg

                        })

                    }
                    false -> {

                        layout_error.visibility = View.VISIBLE
                        layout_main.visibility = View.GONE
                        val tv = layout_error.findViewById<TextView>(R.id.tvError)
                        tv.text = "No Internet Connection!!"

                    }
                }
            }
        }
    }

    fun bindingView(){
        rcvProductList = findViewById(R.id.rcv_products)
        dialogLoading = findViewById(R.id.mainProgress)
        layout_error = findViewById(R.id.layout_error_main)
        layout_main = findViewById(R.id.layout_main)

        itemDecor = SpacesItemDecoration(2,32)
        rcvProductList.layoutManager = GridLayoutManager(this,2)
        rcvProductList.addItemDecoration(itemDecor)
    }

    override fun onItemClicked(productID: Int) {
        val intent = Intent(this, ContentActivity::class.java)
        intent.putExtra("productID",productID)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }
}