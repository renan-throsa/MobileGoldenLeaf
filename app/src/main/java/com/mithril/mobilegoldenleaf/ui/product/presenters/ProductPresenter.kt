package com.mithril.mobilegoldenleaf.ui.product.presenters

import com.mithril.mobilegoldenleaf.asynctask.BaseAsyncTask
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository
import com.mithril.mobilegoldenleaf.retrofit.webclient.ProductWebClient

class ProductPresenter(private val repository: ProductRepository) {

    private val webClient = ProductWebClient()

    fun get(whenSuccess: (List<Product>) -> Unit, whenFail: (error: String?) -> Unit) {
        searchInternally(whenSuccess)
        searchRemotely(whenSuccess, whenFail)
    }

    fun get(id: Long, whenSuccess: (List<Product>) -> Unit, whenFail: (error: String?) -> Unit) {
        searchInternally(id, whenSuccess)
        searchRemotely(id, whenSuccess, whenFail)
    }

    fun get(id: Long, whenSuccess: (categoryFound: Product?) -> Unit) {
        BaseAsyncTask(whenExecute = {
            repository.get(id)
        }, whenFinalize = whenSuccess).execute()
    }


    fun save(product: Product,
             whenSuccess: (product: Product) -> Unit,
             whenFail: (error: String?) -> Unit) {
        saveRemotely(product, whenSuccess, whenFail)
    }

    fun update(product: Product,
               whenSuccess: (newProduct: Product) -> Unit,
               whenFail: (error: String?) -> Unit) {

        updateRemotely(product, whenSuccess, whenFail)
    }


    private fun searchInternally(whenSuccess: (List<Product>) -> Unit) {
        BaseAsyncTask(whenExecute = {
            repository.get()
        }, whenFinalize = whenSuccess)
                .execute()
    }

    private fun searchRemotely(
            whenSuccess: (List<Product>) -> Unit,
            whenFail: (error: String?) -> Unit
    ) {
        webClient.get(
                whenSuccess = { newCategories ->
                    newCategories?.let {
                        saveInternally(newCategories, whenSuccess)
                    }
                }, whenFail = whenFail
        )
    }

    private fun searchInternally(categoryId: Long, whenSuccess: (List<Product>) -> Unit) {
        BaseAsyncTask(whenExecute = {
            repository.productsWith(categoryId)
        }, whenFinalize = whenSuccess)
                .execute()
    }

    private fun searchRemotely(categoryId: Long,
                               whenSuccess: (List<Product>) -> Unit,
                               whenFail: (error: String?) -> Unit
    ) {
        webClient.get(categoryId,
                whenSuccess = { newProducts ->
                    newProducts?.let {
                        saveInternally(newProducts, whenSuccess)
                    }
                }, whenFail = whenFail
        )
    }

    private fun saveRemotely(
            product: Product,
            whenSuccess: (newProduct: Product) -> Unit,
            whenFail: (error: String?) -> Unit
    ) {
        webClient.post(product,
                whenSuccess = {
                    it?.let { productSaved ->
                        productSaved.synchronized = true
                        saveInternally(productSaved, whenSuccess)
                    }
                }, whenFail = whenFail)
    }

    private fun updateRemotely(product: Product,
                               whenSuccess: (updatedProduct: Product) -> Unit,
                               whenFail: (error: String?) -> Unit) {

        webClient.put(product.id,
                product,
                whenSuccess = { updatedCategory ->
                    updatedCategory?.let {
                        saveInternally(updatedCategory, whenSuccess)
                    }
                },
                whenFail = whenFail)
    }

    private fun saveInternally(
            products: List<Product>,
            whenSuccess: (newProducts: List<Product>) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    repository.save(products)
                    repository.get()
                }, whenFinalize = whenSuccess
        ).execute()
    }

    private fun saveInternally(
            product: Product,
            whenSuccess: (newProduct: Product) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    repository.save(product)
                    repository.get(product.id)
                }, whenFinalize = { categoryFound -> whenSuccess(categoryFound) }
        ).execute()
    }

}