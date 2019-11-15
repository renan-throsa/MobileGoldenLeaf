package com.mithril.mobilegoldenleaf.ui.category.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryFormView
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryDelegate
import com.mithril.mobilegoldenleaf.ui.category.presenters.CategoryFormPresenter
import kotlinx.android.synthetic.main.dialog_category_form.view.*


class FormDialog(private val context: Context, private val viewGroup: ViewGroup?,
                 private val delegate: CategoryDelegate, private val categoryId: Long = 0L) : CategoryFormView {

    private val view = createView()
    private val presenter by lazy {
        val repository = MobileGoldenLeafDataBase.getInstance(context).categoryRepository
        CategoryFormPresenter(this, repository)
    }

    private val NEGATIVE_BUTTON_TITLE = "Cancelar"

    private val DIALOG_TITLE: String
        get() {
            return if (categoryId != 0L) {
                "Editar categoria"
            } else {
                "Nova categoria"
            }

        }
    private val POSITIVE_BUTTON_TITLE: String
        get() {
            return if (categoryId != 0L) {
                "Editar"
            } else {
                "Salvar"
            }

        }


    fun show() {
        if (categoryId != 0L) {
            presenter.loadBy(categoryId)
        }
        AlertDialog.Builder(context)
                .setTitle(DIALOG_TITLE)
                .setView(view)
                .setPositiveButton(POSITIVE_BUTTON_TITLE) { _, _ ->
                    createCategory()
                }
                .setNegativeButton(NEGATIVE_BUTTON_TITLE, null)
                .show()

    }

    override fun show(category: Category) {
        view.form_category_title.setText(category.title.toString())
    }

    override fun showInvalidTitleError() {
        Toast.makeText(context, R.string.invalid_title_error, Toast.LENGTH_SHORT)
                .show()
    }

    override fun showSavingCategoryError() {
        Toast.makeText(context, "Não foi possível salvar a categoria remotamente.", Toast.LENGTH_SHORT)
                .show()
    }


    private fun createCategory() {
        val category = if (categoryId != 0L) {
            updateCategory()
        } else {
            saveCategory()
        }
        if (category != null) {
            delegate.delegate(category)
        }


    }

    private fun saveCategory(): Category? {
        val category = Category()
        category.title = view.form_category_title.text.toString()
        return if (presenter.save(category)) {
            category
        } else {
            null
        }

    }

    private fun updateCategory(): Category? {
        val category = Category()
        category.id = categoryId
        category.title = view.form_category_title.text.toString()
        return if (presenter.update(category)) {
            category
        } else {
            null
        }

    }


    private fun createView(): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.dialog_category_form, viewGroup, false)

    }


}




