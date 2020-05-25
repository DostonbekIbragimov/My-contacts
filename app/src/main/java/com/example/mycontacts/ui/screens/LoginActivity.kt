package com.example.mycontacts.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycontacts.R
import com.example.mycontacts.contracts.LoginContract
import com.example.mycontacts.data.repositories.LoginRepository
import com.example.mycontacts.ui.presenters.LoginPresenter
import com.example.mycontacts.utils.extensions.toEditable
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(LoginRepository(), this)
        buttonLogin.setOnClickListener {
            presenter.login()
        }
        loadFromRegisterToLogin()
    }

    private fun loadFromRegisterToLogin() {
        val bundle = intent.extras
        if (bundle != null) presenter.getBundle(bundle)
    }

    override fun makeToast(txt: String) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
    }

    override fun getLogin() = login_username.text.toString()

    override fun getPassword() = login_password.text.toString()
    override fun loginToEditable(login: String) {
        login_username.text = login.toEditable()
    }

    override fun passwordToEditable(password: String) {
        login_password.text = password.toEditable()
    }

    override fun openAdminActivity() {
        startActivity(Intent(this, AdminActivity::class.java))
        finish()
    }

    override fun openContactActivity() {
        startActivity(Intent(this, ContactActivity::class.java))
    }

    override fun clear() {
        login_username.text!!.clear()
        login_password.text!!.clear()
        finish()
    }
}
