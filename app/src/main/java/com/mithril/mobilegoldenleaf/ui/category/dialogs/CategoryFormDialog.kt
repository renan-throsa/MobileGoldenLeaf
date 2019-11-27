package com.mithril.mobilegoldenleaf.ui.category.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import com.mithril.mobilegoldenleaf.ui.category.presenters.CategoryPresenter
import kotlinx.android.synthetic.main.dialog_category_form.view.*


class CategoryFormDialog(private val context: Context, private val viewGroup: ViewGroup?,
                         private val categoryId: Long = 0L) {

    private val view = createView()

    private val presenter by lazy {
        val repository = AppDataBase.getInstance(context).categoryRepository
        CategoryPresenter(repository)
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


    fun show(whenSuccess: (category: Category) -> Unit, whenFail: (error: String?) -> Unit) {
        if (categoryId != 0L) {
            presenter.get(categoryId, whenSuccess = { categoryFound ->
                if (categoryFound != null) {
                    show(categoryFound)
                }
            })
        }
        AlertDialog.Builder(context)
                .setTitle(DIALOG_TITLE)
                .setView(view)
                .setPositiveButton(POSITIVE_BUTTON_TITLE) { _, _ ->
                    save(Category(view.form_category_title.text.toString()),
                            whenSuccess, whenFail)
                }
                .setNegativeButton(NEGATIVE_BUTTON_TITLE, null)
                .show()

    }

    private fun show(category: Category) {
        view.form_category_title.setText(category.title.toString())
    }

    private fun save(category: Category,
                     whenSuccess: (category: Category) -> Unit,
                     whenFail: (error: String?) -> Unit) {

        if (categoryId != 0L) {
            category.id = categoryId
            presenter.update(category, whenSuccess = whenSuccess, whenFail = whenFail)
        } else {
            presenter.save(category, whenSuccess = whenSuccess, whenFail = whenFail)
        }

    }

    private fun createView(): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.dialog_category_form, viewGroup, false)

    }


}




