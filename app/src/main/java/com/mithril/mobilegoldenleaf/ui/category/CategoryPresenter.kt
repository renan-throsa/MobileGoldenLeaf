package com.mithril.mobilegoldenleaf.ui.category

import com.mithril.mobilegoldenleaf.asynctask.BaseAsyncTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.retrofit.webclient.CategoryWebClient

class CategoryPresenter(private val repository: CategoryRepository) {

    private val webClient = CategoryWebClient()

    fun get(whenSucceeded: (List<Category>) -> Unit, whenFailed: (error: String?) -> Unit) {
        searchInternally(whenSucceeded)
        searchRemotely(whenSucceeded, whenFailed)
    }

    fun get(id: Long, whenSucceeded: (categoryRetrieved: Category?) -> Unit) {
        BaseAsyncTask(whenExecute = {
            repository.get(id)
        }, whenFinalize = whenSucceeded).execute()
    }

    fun save(category: Category,
             whenSucceeded: (category: Category) -> Unit,
             whenFailed: (error: String?) -> Unit) {
        saveRemotely(category, whenSucceeded, whenFailed)
    }

    fun update(category: Category,
               whenSucceeded: (newCategory: Category) -> Unit,
               whenFailed: (error: String?) -> Unit) {

        updateRemotely(category, whenSucceeded, whenFailed)
    }


    private fun searchInternally(whenSucceeded: (List<Category>) -> Unit) {
        BaseAsyncTask(whenExecute = {
            repository.get()
        }, whenFinalize = whenSucceeded)
                .execute()
    }

    private fun searchRemotely(
            whenSucceeded: (List<Category>) -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        webClient.get(
                whenSucceeded = { newCategories ->
                    newCategories?.let {
                        saveInternally(newCategories, whenSucceeded)
                    }
                }, whenFailed = whenFailed
        )
    }

    private fun saveRemotely(
            category: Category,
            whenSucceeded: (newCategory: Category) -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        webClient.post(category,
                whenSucceeded = {
                    it?.let { categorySaved ->
                        categorySaved.synchronized = true
                        saveInternally(categorySaved, whenSucceeded)
                    }
                }, whenFailed = whenFailed)
    }

    private fun updateRemotely(category: Category,
                               whenSucceeded: (updatedCategory: Category) -> Unit,
                               whenFailed: (error: String?) -> Unit) {

        webClient.put(category.id,
                category,
                whenSucceeded = { updatedCategory ->
                    updatedCategory?.let {
                        saveInternally(updatedCategory, whenSucceeded)
                    }
                },
                whenFailed = whenFailed)
    }

    private fun saveInternally(
            categories: List<Category>,
            whenSucceeded: (newCategories: List<Category>) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    repository.save(categories)
                    repository.get()
                }, whenFinalize = whenSucceeded
        ).execute()
    }

    private fun saveInternally(
            category: Category,
            whenSucceeded: (newCategory: Category) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    repository.save(category)
                    repository.get(category.id)
                }, whenFinalize = { categoryFound -> whenSucceeded(categoryFound) }
        ).execute()
    }

}