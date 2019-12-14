package com.mithril.mobilegoldenleaf.persistence.repository

class Resource<T>(val data: T, val error: String? = null)

fun <T> createFailedResource(currentResource: Resource<T?>?, it: String?): Resource<T?> {
    if (currentResource != null) {
        return Resource(data = currentResource.data, error = it)
    }
    return Resource(data = null, error = it)

}