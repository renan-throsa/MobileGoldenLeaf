package com.mithril.mobilegoldenleaf.ui.category.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import kotlinx.android.synthetic.main.dialogfragment_category_form.view.*


abstract class FormDialog(private val context: Context, private val title: String, private val positiveButtonTitle: String,
                          private val listener: ConfirmationListener) {

    private val NEGATIVE_BUTTON_TITLE = "Cancelar"
    private var category: Category? = null

    constructor(context: Context, title: String, positiveButtonTitle: String, listener: ConfirmationListener, category: Category)
            : this(context, title, positiveButtonTitle, listener) {
        this.category = category
    }


    fun show() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialogfragment_category_form, null)
        tryToFillOutForm(view)
        AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(positiveButtonTitle) { dialog, witch ->
                    val title = view.form_category_title.text.toString()
                    createCategory(title)
                }
                .setNegativeButton(NEGATIVE_BUTTON_TITLE, null)
                .show()

    }

    private fun tryToFillOutForm(view: View) {
        category?.title = view.form_category_title.text.toString()
    }

    private fun createCategory(title: String) {
        val title: String = title
        val id: Long = fillOutId()
        val category = Category(id, title)
        listener.whenConfirmed(category)

    }

    private fun fillOutId(): Long {
        return category?.id ?: 0L
    }


    interface ConfirmationListener {
        fun whenConfirmed(category: Category)
    }


}




