package com.example.mycontacts.ui.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycontacts.R
import com.example.mycontacts.contracts.AdminContract
import com.example.mycontacts.data.repositories.AdminRepository
import com.example.mycontacts.data.source.room.entity.UserData
import com.example.mycontacts.ui.adapters.AdminAdapter
import com.example.mycontacts.ui.dialogs.AdminDialog
import com.example.mycontacts.ui.presenters.AdminPresenter
import kotlinx.android.synthetic.main.activity_contact.*

class AdminActivity : AppCompatActivity(), AdminContract.View {
    private val adapter = AdminAdapter()
    private lateinit var presenter: AdminContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        presenter = AdminPresenter(AdminRepository(), this)
        title = "All users"
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        adapter.setOnItemClickListener(presenter::clickUser)
        adapter.setOnItemDeleteListener(presenter::deleteUser)
        adapter.setOnItemEditListener(presenter::editUser)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuAdd -> presenter.openAddUser()
        }
        return true
    }

    override fun loadData(list: List<UserData>) {
        adapter.submitList(list)
    }

    override fun openContactActivity() {
        startActivity(Intent(this, ContactActivity::class.java))
    }

    override fun removeItem(data: UserData) {
        adapter.removeItem(data)
    }

    override fun updateItem(data: UserData) {
        adapter.updateItem(data)
    }

    override fun insertItem(data: UserData) {
        adapter.insertItem(data)
    }

    override fun openEditDialog(data: UserData) {
        val dialog = AdminDialog(this, "Edit")
        dialog.setStudentData(data)
        dialog.setOnClickListener(presenter::confirmEditUser)
        dialog.show()
    }

    override fun openAddDialog() {
        val dialog = AdminDialog(this, "Add")
        dialog.setOnClickListener(presenter::addUser)
        dialog.show()
    }

    override fun makeToast(txt: String) {
        Toast.makeText(this,txt, Toast.LENGTH_SHORT).show()
    }
}
