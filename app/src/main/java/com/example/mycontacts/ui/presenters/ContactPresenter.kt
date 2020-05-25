package com.example.mycontacts.ui.presenters

import android.os.Handler
import android.os.Looper
import com.example.mycontacts.contracts.ContactContract
import com.example.mycontacts.data.source.room.entity.ContactData
import java.util.concurrent.Executors

class ContactPresenter(
    private val model: ContactContract.Model,
    private val view: ContactContract.View
) : ContactContract.Presenter {

    private val executor = Executors.newSingleThreadExecutor()
    private val handle = Handler(Looper.getMainLooper())

    init {
        runOnWorkerThread {
            val ls = model.filterCourseById()
            runOnUIThread { view.loadData(ls) }
        }
    }

    override fun deleteContact(data: ContactData) {
        runOnWorkerThread {
            model.delete(data)
            runOnUIThread { view.deleteItem(data) }
        }
    }

    override fun editContact(data: ContactData) {
        runOnUIThread { view.openEditDialog(data) }
    }

    override fun confirmEditContact(data: ContactData) {
        runOnWorkerThread {
            model.update(data)
            runOnUIThread { view.updateItem(data) }
        }
    }

    override fun createContact(data: ContactData) {
        runOnWorkerThread {
            model.insert(data)
            runOnUIThread { view.insertItem(data) }
        }
    }

    override fun openAddContact() {
        runOnUIThread { view.openInsertDialog() }
    }

    override fun deleteAll() {
        runOnWorkerThread {
            model.deleteAll()
            runOnUIThread { view.removeAllItem() }
        }
    }

    private fun runOnUIThread(f: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            f()
        } else { handle.post { f() } }
    }

    private fun runOnWorkerThread(f: () -> Unit) { executor.execute { f() } }
}