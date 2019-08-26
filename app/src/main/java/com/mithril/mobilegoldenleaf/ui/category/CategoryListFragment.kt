package com.mithril.mobilegoldenleaf.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.CategoryAdapter
import com.mithril.mobilegoldenleaf.asynctask.category.GetCategoryTask
import com.mithril.mobilegoldenleaf.asynctask.category.SaveCategoryTask
import com.mithril.mobilegoldenleaf.asynctask.category.UpdateCategoryTask
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.delegate.CategoryDelegate
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.ui.category.dialog.AddCategoryDialog
import com.mithril.mobilegoldenleaf.ui.category.dialog.AlterCategoryDialog
import kotlinx.android.synthetic.main.activity_products_list.*

class CategoryListFragment : Fragment() {

    private val dao by lazy { MobileGoldenLeafDataBase.getInstance(requireContext()).categoryRepository }
    private val activityView by lazy { activity?.window?.decorView as ViewGroup }
    private val adapter by lazy { CategoryAdapter(requireContext()) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboad, null)
    }

    private fun configureList() {
        with(product_list_listview) {
            setOnItemClickListener { _, _, position, _ ->
                val category = adapter.getItem(position)
                callEditingDialog(category as Category)
            }

        }
    }

    private fun callEditingDialog(category: Category) {

        AlterCategoryDialog(activityView, context)
                .call(category, CategoryDelegate { category ->
                    UpdateCategoryTask(dao, category).execute()
                    updateCategories()
                })

    }


    private fun callAddingDialog() {
        AddCategoryDialog(activityView, context)
                .call(CategoryDelegate { category ->
                    SaveCategoryTask(dao, category).execute()
                    updateCategories()
                })
    }

    private fun updateCategories() {
        GetCategoryTask(dao, adapter).execute()
    }

}