package com.mithril.mobilegoldenleaf.ui.category.presenters

import com.mithril.mobilegoldenleaf.asynctask.category.GetCategoryTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryListView

class CategoryListPresenter(private val view: CategoryListView,
                            private val repository: CategoryRepository) {

    fun searchCategories(term: String) {
        if (term.isEmpty()) {
            view.showCategories(GetCategoryTask(repository).execute().get())
        } else {
            view.showCategories(repository.search(term))
        }
    }


}