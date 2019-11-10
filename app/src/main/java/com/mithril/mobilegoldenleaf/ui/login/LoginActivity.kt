package com.mithril.mobilegoldenleaf.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Clerk
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.retrofit.RetrofitInitializer
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.login.interfaces.LoginFormView
import com.mithril.mobilegoldenleaf.ui.login.presenters.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), LoginFormView {

    private val presenter by lazy {
        val repository = MobileGoldenLeafDataBase.getInstance(this).clerkRepository
        LoginPresenter(this, repository)
    }


    override fun showLoginError() {
        val toast = Toast.makeText(this, R.string.getting_token_error, Toast.LENGTH_SHORT)
        toast.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        form_btn_login.setOnClickListener {

            if (form_login_Password.text.toString().isEmpty()) {
                form_login_Password.error = getString(R.string.error_msg_password)
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(form_login_Email.text.toString()).matches()
                    || form_login_Email.text.toString().isEmpty()
            ) {
                form_login_Email.error = getString(R.string.error_msg_email)
            } else {
                presenter.doLogin(form_login_Email.text.toString(), form_login_Password.text.toString())
            }
        }
    }


    override fun login(clerk: Clerk) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("clerk", clerk)
        startActivity(intent)
    }


}