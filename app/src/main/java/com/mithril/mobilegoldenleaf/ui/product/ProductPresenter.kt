package com.mithril.mobilegoldenleaf.ui.product

import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.asynctask.BaseAsyncTask
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository
import com.mithril.mobilegoldenleaf.retrofit.webclient.ProductWebClient

class ProductPresenter(private val productRepository: ProductRepository) {

    private val productWebClient = ProductWebClient()
    private val validator = ProductValidator()

    fun get(whenSucceeded: (List<Product>) -> Unit, whenFailed: (error: String?) -> Unit) {
        searchInternally(whenSucceeded)
        searchRemotely(whenSucceeded, whenFailed)
    }

    fun get(categoryId: Long, whenSucceeded: (List<Product>) -> Unit, whenFailed: (error: String?) -> Unit) {
        searchInternally(categoryId, whenSucceeded)
        searchRemotely(categoryId, whenSucceeded, whenFailed)
    }

    fun get(productId: Long, whenSucceeded: (productFound: Product?) -> Unit) {
        BaseAsyncTask(whenExecute = {
            productRepository.get(productId)
        }, whenFinalize = whenSucceeded).execute()
    }


    fun save(product: Product,
             whenSucceeded: (product: Product) -> Unit,
             whenFailed: (error: String?) -> Unit) {
        if (validator.validate(product)) {
            saveRemotely(product, whenSucceeded, whenFailed)
        } else {
            whenFailed(R.string.product_invalid_error.toString())
        }

    }

    fun update(product: Product,
               whenSucceeded: (newProduct: Product) -> Unit,
               whenFailed: (error: String?) -> Unit) {
        if (validator.validate(product)) {
            updateRemotely(product, whenSucceeded, whenFailed)
        } else {
            whenFailed(R.string.product_invalid_error.toString())
        }
    }


    private fun searchInternally(whenSucceeded: (List<Product>) -> Unit) {
        BaseAsyncTask(whenExecute = {
            productRepository.get()
        }, whenFinalize = whenSucceeded)
                .execute()
    }

    private fun searchRemotely(
            whenSucceeded: (List<Product>) -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        productWebClient.get(
                whenSucceeded = { newCategories ->
                    newCategories?.let {
                        saveInternally(newCategories, whenSucceeded)
                    }
                }, whenFailed = whenFailed
        )
    }

    private fun searchInternally(categoryId: Long, whenSucceeded: (List<Product>) -> Unit) {
        BaseAsyncTask(whenExecute = {
            productRepository.productsWith(categoryId)
        }, whenFinalize = whenSucceeded)
                .execute()
    }

    private fun searchRemotely(categoryId: Long,
                               whenSucceeded: (List<Product>) -> Unit,
                               whenFailed: (error: String?) -> Unit
    ) {
        productWebClient.get(categoryId,
                whenSucceeded = { newProducts ->
                    newProducts?.let {
                        saveInternally(newProducts, whenSucceeded)
                    }
                }, whenFailed = whenFailed
        )
    }

    private fun saveRemotely(
            product: Product,
            whenSucceeded: (newProduct: Product) -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        productWebClient.post(product,
                whenSucceeded = {
                    it?.let { productSaved ->
                        productSaved.synchronized = true
                        saveInternally(productSaved, whenSucceeded)
                    }
                }, whenFailed = whenFailed)
    }

    private fun updateRemotely(product: Product,
                               whenSucceeded: (updatedProduct: Product) -> Unit,
                               whenFailed: (error: String?) -> Unit) {

        productWebClient.put(product.id,
                product,
                whenSucceeded = { updatedCategory ->
                    updatedCategory?.let {
                        saveInternally(updatedCategory, whenSucceeded)
                    }
                },
                whenFailed = whenFailed)
    }

    private fun saveInternally(
            products: List<Product>,
            whenSucceeded: (newProducts: List<Product>) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    productRepository.save(products)
                    productRepository.get()
                }, whenFinalize = whenSucceeded
        ).execute()
    }

    private fun saveInternally(
            product: Product,
            whenSucceeded: (newProduct: Product) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    productRepository.save(product)
                    productRepository.get(product.id)
                }, whenFinalize = { categoryFound -> whenSucceeded(categoryFound) }
        ).execute()
    }

}