package com.example.mycontacts.ui.presenters

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.mycontacts.contracts.LoginContract
import com.example.mycontacts.data.preferences.LocalStorage
import com.example.mycontacts.data.source.room.dao.LoginPassword
import java.util.concurrent.Executors

class LoginPresenter(private val model: LoginContract.Model, private val view: LoginContract.View) :
    LoginContract.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handle = Handler(Looper.getMainLooper())

    override fun getBundle(bundle: Bundle) {
        view.loginToEditable(bundle.getString("LOGIN", "null"))
        view.passwordToEditable(bundle.getString("PASSWORD", "null"))
    }

    override fun login() {
        if (view.getLogin().isEmpty() && view.getPassword().isEmpty()) {
            view.makeToast("Enter login and password")
            return
        }
        if (view.getLogin().isEmpty()) {
            view.makeToast("Enter login!")
            return
        }
        if (view.getPassword().isEmpty()) {
            view.makeToast("Enter password")
            return
        }

        if (view.getLogin() == "admin" && view.getPassword() == "password") {
            view.openAdminActivity()
            return
        }
        runOnWorkerThread {
            var busy = false
            val ls = model.getLoginPassword()
            if (!ls.contains(LoginPassword(view.getLogin(), view.getPassword()))) {
                busy = true
                runOnUIThread {
                    view.makeToast("This account isn't exist!\nYou must register!")
                }
            }
            if (!busy){
                runOnWorkerThread {
                    val ls1 = model.getUser(view.getLogin(), view.getPassword())
                    LocalStorage.instance.activeUser = ls1
                    runOnUIThread {
                        view.openContactActivity()
                        view.clear()
                    }
                }
            }
        }
    }
    private fun runOnUIThread(f: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            f()
        } else { handle.post { f() } }
    }

    private fun runOnWorkerThread(f: () -> Unit) { executor.execute { f() } }
}