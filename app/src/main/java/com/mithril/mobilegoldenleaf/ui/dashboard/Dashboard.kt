package com.mithril.mobilegoldenleaf.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R

class Dashboard : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboad, null)
    }

    companion object {
        const val TAG_PRODUCT_LIST = "tagDashboard"
        fun newInstance(): Dashboard {
            return Dashboard()
        }
    }
}