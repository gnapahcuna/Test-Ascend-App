package com.paiwaddev.testascendapp.views.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.paiwaddev.testascendapp.R
import com.paiwaddev.testascendapp.utils.network.NetworkUtil
import com.paiwaddev.testascendapp.viewModels.ProductViewModel
import com.paiwaddev.testascendapp.views.SpacesItemDecoration
import com.paiwaddev.testascendapp.views.adapter.ProductAdapter
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.DecimalFormat


class ContentActivity : AppCompatActivity() {
    private val viewModel: ProductViewModel by viewModel()
    private val networkMonitor: NetworkUtil by inject()

    private lateinit var tvTitle: TextView
    private lateinit var tvContent: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvIsNews: TextView
    private lateinit var imgView: ImageView
    private lateinit var dialogLoading: View
    private lateinit var layout_error: View
    private lateinit var layout_main: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        val toolbar: Toolbar = findViewById(R.id.toolbar_content)
        val back: LinearLayout = toolbar.findViewById(R.id.back_button)
        back.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }
        setSupportActionBar(toolbar)

        bindingView()
        val proID = getIntent().getIntExtra("productID", 0)

        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        layout_error.visibility = View.GONE
                        layout_main.visibility = View.VISIBLE
                        viewModel.getProductContent(proID,dialogLoading).observe(this, { product ->
                            val dec = DecimalFormat("#,###.##")

                            if (product.isNewProduct)
                                tvIsNews.text = "New"

                            Glide.with(this)
                                .load(product.imageProduct)
                                .centerCrop()
                                .error(R.drawable.unnamed)
                                .into(imgView)

                            tvTitle.text = product.productName
                            tvContent.text = product.productContent
                            tvPrice.text = dec.format(product.productPrice)
                        })

                        viewModel.errorMessage().observe(this, { errorMsg ->
                            layout_error.visibility = View.VISIBLE
                            layout_main.visibility = View.GONE
                            val tv = layout_error.findViewById<TextView>(R.id.tvError)
                            tv.text = errorMsg
                        })

                    }
                    false -> {
                        println("No Internet")
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
        imgView = findViewById(R.id.img_view_product_content)
        tvTitle = findViewById(R.id.tv_product_name_content)
        tvContent = findViewById(R.id.tv_product_content)
        tvPrice = findViewById(R.id.tv_product_price_content)
        tvIsNews = findViewById(R.id.tv_new_product_content)
        dialogLoading = findViewById(R.id.contentProgress)
        layout_error = findViewById(R.id.layout_error_content)
        layout_main = findViewById(R.id.layout_main_content)
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