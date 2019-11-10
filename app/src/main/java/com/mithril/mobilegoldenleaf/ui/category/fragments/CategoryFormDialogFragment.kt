package com.mithril.mobilegoldenleaf.ui.category.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryFormView
import com.mithril.mobilegoldenleaf.ui.category.interfaces.OnCategorySavedListener
import com.mithril.mobilegoldenleaf.ui.category.presenters.CategoryFormPresenter
import kotlinx.android.synthetic.main.dialogfragment_category_form.*

class CategoryFormDialogFragment : DialogFragment(), CategoryFormView {

    private val categoryId = arguments?.getLong(EXTRA_CATEGORY_ID, 0L) ?: 0L
    private lateinit var activityContext: MainActivity

    private val presenter by lazy {
        val repository = MobileGoldenLeafDataBase.getInstance(activityContext).categoryRepository
        CategoryFormPresenter(this, repository)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NORMAL, R.style.CustomDialogFragment)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityContext = activity as MainActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialogfragment_category_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (categoryId != 0L) {
            presenter.loadBy(categoryId)
            dialog?.setTitle(R.string.edit_category)
        }
        dialog?.setTitle(R.string.add_category)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_action_concluded_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.fragment_form_action_concluded) {
            val category = if (categoryId != 0L) {
                updateCategory()
            } else {
                saveCategory()
            }
            if (category != null) {
                val listener = targetFragment as OnCategorySavedListener
                listener.onCategorySaved()

            }
            dialog?.dismiss()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun show(category: Category) {
        form_category_title.setText(category.title.toString())
    }

    override fun showInvalidTitleError() {
        Toast.makeText(context, R.string.invalid_title_error, Toast.LENGTH_SHORT)
                .show()
    }

    override fun showSavingCategoryError() {
        Toast.makeText(context, "Não foi possível salvar a categoria remotamente.", Toast.LENGTH_SHORT)
                .show()
    }


    private fun saveCategory(): Category? {
        val category = Category()
        category.title = form_category_title.text.toString()
        return if (presenter.save(category)) {
            category
        } else {
            null
        }

    }

    private fun updateCategory(): Category? {
        val category = Category()
        category.id = categoryId
        category.title = form_category_title.text.toString()

        return if (presenter.update(category)) {
            category
        } else {
            null
        }

    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null)
            show(fm, DIALOG_TAG)
    }


    companion object {
        private const val DIALOG_TAG = "categoryFormFragment"
        private const val EXTRA_CATEGORY_ID = "categoryId"

        fun newInstance(id: Long = 0L): CategoryFormDialogFragment {
            val fragment = CategoryFormDialogFragment()
            val args = Bundle()
            args.putLong(EXTRA_CATEGORY_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}