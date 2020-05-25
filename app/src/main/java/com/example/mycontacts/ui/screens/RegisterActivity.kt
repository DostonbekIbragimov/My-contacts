package com.example.mycontacts.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycontacts.R
import com.example.mycontacts.contracts.RegisterContract
import com.example.mycontacts.data.repositories.RegisterRepository
import com.example.mycontacts.ui.presenters.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterContract.View {
    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = RegisterPresenter(RegisterRepository(), this)
        buttonRegister.setOnClickListener {
            presenter.clickOnRegisterItem()
        }
    }

    override fun makeToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    override fun createAccount() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("LOGIN", getLogin())
        intent.putExtra("PASSWORD", getPassword())
        startActivity(intent)
        finish()
    }

    override fun clear() {
        register_full_name.text!!.clear()
        register_password.text!!.clear()
        register_username.text!!.clear()
        re_register_password.text!!.clear()
    }

    override fun getFullName() = register_full_name.text.toString()

    override fun getLogin() = register_username.text.toString()

    override fun getPassword() = register_password.text.toString()

    override fun getConfirmPassword() = re_register_password.text.toString()
}

