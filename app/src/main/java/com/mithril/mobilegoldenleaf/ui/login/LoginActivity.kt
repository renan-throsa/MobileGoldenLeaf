package com.mithril.mobilegoldenleaf.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Clerk
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.login.interfaces.LoginFormView
import com.mithril.mobilegoldenleaf.ui.login.presenters.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginFormView {

    private val presenter by lazy {
        val repository = MobileGoldenLeafDataBase.getInstance(this).clerkRepository
        LoginPresenter(this, repository)
    }


    override fun showLoginError() {
        val toast = Toast.makeText(this, R.string.getting_clerk_error, Toast.LENGTH_SHORT)
        toast.show()
        form_btn_login.isEnabled = true
    }

    override fun showLoginError(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
        form_btn_login.isEnabled = true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        form_btn_login.setOnClickListener {
            form_btn_login.isEnabled = false

            if (form_login_Password.text.toString().isEmpty()) {
                form_login_Password.error = getString(R.string.error_msg_password)
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(form_login_Email.text.toString()).matches()
                    || form_login_Email.text.toString().isEmpty()
            ) {
                form_login_Email.error = getString(R.string.error_msg_email)
            } else {
                //presenter.getClerk(form_login_Email.text.toString(), form_login_Password.text.toString())
                val clerk = Clerk(1, "Renan Rosa", "91998291510", "renan@email.com", "123456")
                login(clerk)
            }
        }
    }


    override fun login(clerk: Clerk) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("clerk", clerk)
        startActivity(intent)
    }


    override fun onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true)
    }


}