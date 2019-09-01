package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Category {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var title: String? = null

    constructor()

    @Ignore
    constructor(title: String) {
        this.title = title
    }

    @Ignore
    constructor(id: Long, title: String) {
        this.id = id
        this.title = title
    }


    override fun toString(): String {
        return "Category{" +
                "title='" + title + '\''.toString() +
                '}'.toString()
    }
}
