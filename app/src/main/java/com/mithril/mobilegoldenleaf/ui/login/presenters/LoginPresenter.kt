package com.mithril.mobilegoldenleaf.ui.login.presenters

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.asynctask.clerk.GetClerkTask
import com.mithril.mobilegoldenleaf.asynctask.clerk.SaveClerkTask
import com.mithril.mobilegoldenleaf.models.Clerk
import com.mithril.mobilegoldenleaf.persistence.repository.ClerkRepository
import com.mithril.mobilegoldenleaf.retrofit.RetrofitInitializer
import com.mithril.mobilegoldenleaf.ui.login.interfaces.LoginFormView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private val view: LoginFormView, private val repository: ClerkRepository) {

    fun getClerk(email: String, password: String) {
        val service = RetrofitInitializer().clerkService()
        val call = service.getClerk(email, password)

        call.enqueue(object : Callback<Clerk?> {
            override fun onResponse(call: Call<Clerk?>, response: Response<Clerk?>) {
                if (response.isSuccessful) {
                    response.body()?.let { clerk ->
                        view.login(clerk)
                        // It will surely be saved, but not immediately.
                        SaveClerkTask(clerk, repository).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                    }
                } else {
                    view.showLoginError(response.message())
                }

            }

            override fun onFailure(call: Call<Clerk?>, t: Throwable) {
                val clerk = GetClerkTask(email, password, repository).execute().get()
                if (clerk != null) {
                    view.login(clerk)
                } else {
                    view.showLoginError()
                }
            }

        })


    }


}