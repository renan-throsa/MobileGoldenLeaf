package com.mithril.mobilegoldenleaf.ui.category.presenters

import com.mithril.mobilegoldenleaf.asynctask.BaseAsyncTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.retrofit.webclient.CategoryWebClient

class CategoryPresenter(private val repository: CategoryRepository) {

    private val webClient = CategoryWebClient()

    fun get(whenSuccess: (List<Category>) -> Unit, whenFail: (error: String?) -> Unit) {
        searchInternally(whenSuccess)
        searchRemotely(whenSuccess, whenFail)
    }

    fun get(id: Long, whenSuccess: (categoryFound: Category?) -> Unit) {
        BaseAsyncTask(whenExecute = {
            repository.get(id)
        }, whenFinalize = whenSuccess).execute()
    }

    fun save(category: Category,
             whenSuccess: (category: Category) -> Unit,
             whenFail: (error: String?) -> Unit) {
        saveRemotely(category, whenSuccess, whenFail)
    }

    fun update(category: Category,
               whenSuccess: (newCategory: Category) -> Unit,
               whenFail: (error: String?) -> Unit) {

        updateRemotely(category, whenSuccess, whenFail)
    }


    private fun searchInternally(whenSuccess: (List<Category>) -> Unit) {
        BaseAsyncTask(whenExecute = {
            repository.get()
        }, whenFinalize = whenSuccess)
                .execute()
    }

    private fun searchRemotely(
            whenSuccess: (List<Category>) -> Unit,
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

    private fun saveRemotely(
            category: Category,
            whenSuccess: (newCategory: Category) -> Unit,
            whenFail: (error: String?) -> Unit
    ) {
        webClient.post(category,
                whenSuccess = {
                    it?.let { categorySaved ->
                        categorySaved.synchronized = true
                        saveInternally(categorySaved, whenSuccess)
                    }
                }, whenFail = whenFail)
    }

    private fun updateRemotely(category: Category,
                               whenSuccess: (updatedCategory: Category) -> Unit,
                               whenFail: (error: String?) -> Unit) {

        webClient.put(category.id,
                category,
                whenSuccess = { updatedCategory ->
                    updatedCategory?.let {
                        saveInternally(updatedCategory, whenSuccess)
                    }
                },
                whenFail = whenFail)
    }

    private fun saveInternally(
            categories: List<Category>,
            whenSuccess: (newCategories: List<Category>) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    repository.save(categories)
                    repository.get()
                }, whenFinalize = whenSuccess
        ).execute()
    }

    private fun saveInternally(
            category: Category,
            whenSuccess: (newCategory: Category) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    repository.save(category)
                    repository.get(category.id)
                }, whenFinalize = { categoryFound -> whenSuccess(categoryFound) }
        ).execute()
    }

}