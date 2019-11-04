package com.mithril.mobilegoldenleaf.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.ui.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        form_btn_login.setOnClickListener {

            if (form_login_Password.text.toString().isEmpty()) {
                form_login_Password.error = getString(R.string.error_msg_password)
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(form_login_Email.text.toString()).matches()) {
                form_login_Email.error = getString(R.string.error_msg_email)
            } else {
                login()
            }
        }
    }


    private fun login() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("token", "token")
        startActivity(intent)
    }


}