package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Category {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var title: String? = null


    @Ignore
    constructor(title: String) {
        this.title = title
    }

    @Ignore
    constructor(id: Int, title: String) {
        this.id = id
        this.title = title
    }


    override fun toString(): String {
        return "Category{" +
                "title='" + title + '\''.toString() +
                '}'.toString()
    }
}
