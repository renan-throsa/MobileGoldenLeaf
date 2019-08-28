package com.mithril.mobilegoldenleaf.ui.product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductDetailsView
import com.mithril.mobilegoldenleaf.ui.product.presenters.ProductDetailsPresenter

class ProductDetailsFragment : Fragment(), ProductDetailsView {

    private val presenter = ProductDetailsPresenter(this,
            MobileGoldenLeafDataBase.getInstance(requireContext()).productRepository)
    private var product: Product? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadProductDetails(arguments?.getLong(EXTRA_PRODUCT_ID, 0) ?: 0)

    }

    override fun showProductDetails(product: Product) {
        this.product = product
        // Set the fields in the viewText...
    }


    companion object {
        const val TAG_DETAILS = "tagDetalhe"
        private const val EXTRA_PRODUCT_ID = "productId"

        fun newInstance(id: Long): ProductDetailsFragment {
            val fragment = ProductDetailsFragment()
            val args = Bundle()
            args.putLong(EXTRA_PRODUCT_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}
