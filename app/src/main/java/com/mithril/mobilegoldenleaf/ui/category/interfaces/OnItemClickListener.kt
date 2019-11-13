package com.mithril.mobilegoldenleaf.ui.category.interfaces

import com.mithril.mobilegoldenleaf.models.Category

interface OnItemClickListener {
    fun onItemClick(position: Int, category: Category)
}