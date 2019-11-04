package com.mithril.mobilegoldenleaf.asynctask.remote

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Category
import retrofit2.Call
import retrofit2.Response

class GetCategoryRemotelyTask(private val repository: Call<List<Category>>) : AsyncTask<Unit, Unit, Response<List<Category>>>() {

    override fun doInBackground(vararg p0: Unit?): Response<List<Category>> {
        return repository.execute()
    }


}
