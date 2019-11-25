package com.mithril.mobilegoldenleaf.asynctask

import android.os.AsyncTask

class BaseAsyncTask<T>(
        private val whenExecute: () -> T,
        private val whenFinalize: (result: T) -> Unit
) : AsyncTask<Void, Void, T>() {

    override fun doInBackground(vararg params: Void?) = whenExecute()

    override fun onPostExecute(result: T) {
        super.onPostExecute(result)
        whenFinalize(result)
    }
}