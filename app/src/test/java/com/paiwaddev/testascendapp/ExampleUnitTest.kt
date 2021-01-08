package com.paiwaddev.testascendapp

import com.paiwaddev.paiwadpos.utils.module.productModule
import com.paiwaddev.testascendapp.data.model.Product
import com.paiwaddev.testascendapp.views.adapter.ProductAdapter
import com.paiwaddev.testascendapp.views.ui.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.Mock


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest: KoinTest {
    @Mock
    private lateinit var mockContext: MainActivity
    @Mock
    private lateinit var itemClick: ProductAdapter.ItemListener

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    var products = listOf<Product>(Product(1, "", "", false, 25.00))
    val adapter by inject<ProductAdapter>() { parametersOf(products, this) }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(productModule)
    }
}