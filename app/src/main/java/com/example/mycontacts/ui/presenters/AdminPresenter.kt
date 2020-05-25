package com.example.mycontacts.ui.presenters

import android.os.Handler
import android.os.Looper
import com.example.mycontacts.contracts.AdminContract
import com.example.mycontacts.data.source.room.dao.LoginPassword
import com.example.mycontacts.data.source.room.entity.UserData
import java.util.concurrent.Executors

class AdminPresenter(private val model: AdminContract.Model, private val view: AdminContract.View) :
    AdminContract.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handle = Handler(Looper.getMainLooper())

    init {
        runOnWorkerThread {
            val ls = model.getAll()
            if (ls.size != -1) {
                runOnUIThread {
                    view.loadData(ls)
                }
            }
        }
    }

    override fun clickUser(data: UserData) {
        runOnWorkerThread {
            model.setActiveUser(data)
            runOnUIThread {
                view.openContactActivity()
            }
        }
    }

    override fun deleteUser(data: UserData) {
        runOnWorkerThread {
            model.delete(data)
            runOnUIThread { view.removeItem(data) }
        }
    }

    override fun addUser(data: UserData) {
        runOnWorkerThread {
            var busy = false
            val ls = model.getLoginPasswords()
            if (ls.contains(LoginPassword(data.login, data.password))
                || (data.login == "admin" && data.password == "password")
            ) {
                busy = true
                runOnUIThread { view.makeToast("This account already exist!") }
            }
            if (!busy) {
                runOnWorkerThread {
                    model.insert(data)
                    runOnUIThread { view.insertItem(data) }
                }
            }
        }
    }

    override fun openAddUser() {
        view.openAddDialog()
    }

    override fun editUser(data: UserData) {
        runOnUIThread { view.openEditDialog(data) }
    }

    override fun confirmEditUser(data: UserData) {
        runOnWorkerThread {
            model.edit(data)
            runOnUIThread { view.updateItem(data) }
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