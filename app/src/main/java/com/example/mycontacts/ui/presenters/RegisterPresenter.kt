package com.example.mycontacts.ui.presenters

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.mycontacts.contracts.RegisterContract
import com.example.mycontacts.data.source.room.dao.LoginPassword
import java.util.concurrent.Executors

class RegisterPresenter(
    private val model: RegisterContract.Model,
    private val view: RegisterContract.View
) : RegisterContract.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handle = Handler(Looper.getMainLooper())

    override fun clickOnRegisterItem() {
        if (view.getFullName().isEmpty()) {
            runOnUIThread {
                view.makeToast("Enter user's full name!")
            }
            return
        }
        if (view.getLogin().isEmpty()) {
            runOnUIThread {
                view.makeToast("Enter login!")

            }
            return
        }
        if (view.getPassword().isEmpty()) {
            runOnUIThread {
                view.makeToast("Enter password!")
            }
            return
        }

        if (view.getConfirmPassword() != view.getPassword()) {
            runOnUIThread {
                view.makeToast("Confirm password isn't the same as password!")
            }
            return
        }

        runOnWorkerThread {
            var busy = false
            val ls = model.getUsers()
            if (ls.contains(LoginPassword(view.getLogin(), view.getPassword()))) {
                busy = true
                runOnUIThread {
                    view.makeToast("This account already exist!")
                }
            }

            if (!busy) {
                runOnWorkerThread {
                    model.addUser(view.getFullName(), view.getLogin(), view.getPassword())
                    view.createAccount()
                    runOnUIThread {
                        Log.d("TTT", "3")
                        view.makeToast("Successfully!")
                        view.clear()
                    }
                }
            }
        }
    }

    private fun runOnUIThread(f: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            f()
        } else {
            handle.post { f() }
        }
    }

    private fun runOnWorkerThread(f: () -> Unit) {
        executor.execute { f() }
    }
}