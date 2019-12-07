package com.mithril.mobilegoldenleaf.ui.category

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import kotlinx.android.synthetic.main.dialog_category_form.view.*


class CategoryFormDialog(private val context: Context, private val viewGroup: ViewGroup?,
                         private val categoryId: Long = 0L) {

    private val view = createView()
    private val NEGATIVE_BUTTON_TITLE = "Cancelar"
    private val POSITIVE_BUTTON_TITLE = "Salvar"

    private val presenter by lazy {
        val repository = AppDataBase.getInstance(context).categoryRepository
        CategoryPresenter(repository)
    }

    private val DIALOG_TITLE: String
        get() {
            return if (categoryId != 0L) {
                "Editar categoria"
            } else {
                "Nova categoria"
            }

        }


    fun show(whenSucceeded: (category: Category) -> Unit, whenFailed: (error: String?) -> Unit) {
        if (categoryId != 0L) {
            presenter.get(categoryId, whenSucceeded = { categoryFound ->
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
                            whenSucceeded, whenFailed)
                }
                .setNegativeButton(NEGATIVE_BUTTON_TITLE, null)
                .show()

    }

    private fun show(category: Category) {
        view.form_category_title.setText(category.title.toString())
    }

    private fun save(category: Category,
                     whenSucceeded: (category: Category) -> Unit,
                     whenFailed: (error: String?) -> Unit) {


        if (categoryId != 0L) {
            category.id = categoryId
            presenter.update(category, whenSucceeded = whenSucceeded, whenFailed = whenFailed)
        } else {
            presenter.save(category, whenSucceeded = whenSucceeded, whenFailed = whenFailed)
        }

    }

    private fun createView(): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.dialog_category_form, viewGroup, false)

    }


}




