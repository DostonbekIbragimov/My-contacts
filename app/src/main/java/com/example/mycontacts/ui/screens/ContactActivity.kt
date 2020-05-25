package com.example.mycontacts.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycontacts.R
import com.example.mycontacts.contracts.ContactContract
import com.example.mycontacts.data.preferences.LocalStorage
import com.example.mycontacts.data.repositories.ContactRepository
import com.example.mycontacts.data.source.room.entity.ContactData
import com.example.mycontacts.ui.adapters.ContactAdapter
import com.example.mycontacts.ui.dialogs.ContactDialog
import com.example.mycontacts.ui.presenters.ContactPresenter
import com.getbase.floatingactionbutton.FloatingActionsMenu
import kotlinx.android.synthetic.main.activity_contact.*

class ContactActivity : AppCompatActivity(), ContactContract.View {
    private val adapter = ContactAdapter()
    private lateinit var presenter: ContactContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        presenter = ContactPresenter(ContactRepository(), this)
        Log.d("TTT", "Active user: ${LocalStorage.instance.activeUser}")
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        adapter.setOnItemDeleteListener(presenter::deleteContact)
        adapter.setOnItemEditListener(presenter::editContact)
        initFloatingActionButtonMenu()
    }

    private fun initFloatingActionButtonMenu() {
        multiple_menu_check.setOnFloatingActionsMenuUpdateListener(object :
            FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
            override fun onMenuExpanded() {
                floating_menu_background_check.visibility = View.VISIBLE
            }

            override fun onMenuCollapsed() {
                floating_menu_background_check.visibility = View.GONE
            }
        })

        action_add_contact_queue_check.setOnClickListener {
            presenter.openAddContact()
            multiple_menu_check.collapse()
        }
        action_delete_to_queue_check.setOnClickListener {
            presenter.deleteAll()
            multiple_menu_check.collapse()
        }
    }

    override fun loadData(list: List<ContactData>) {
        adapter.submitList(list)
    }

    override fun makeToast(txt: String) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
    }

    override fun deleteItem(data: ContactData) {
        adapter.removeItem(data)
    }

    override fun updateItem(data: ContactData) {
        adapter.updateItem(data)
    }

    override fun insertItem(data: ContactData) {
        adapter.insertItem(data)
    }

    override fun removeAllItem() {
        adapter.removeAll()
    }

    override fun openEditDialog(data: ContactData) {
        val dialog = ContactDialog(this, "Edit")
        dialog.setStudentData(data)
        dialog.setOnClickListener(presenter::confirmEditContact)
        dialog.show()
    }

    override fun openInsertDialog() {
        val dialog = ContactDialog(this, "Add")
        dialog.setOnClickListener(presenter::createContact)
        dialog.show()
    }
}