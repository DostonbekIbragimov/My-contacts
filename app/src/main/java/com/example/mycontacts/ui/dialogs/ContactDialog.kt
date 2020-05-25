package com.example.mycontacts.ui.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.mycontacts.R
import com.example.mycontacts.app.App
import com.example.mycontacts.data.preferences.LocalStorage
import com.example.mycontacts.data.source.room.AppDatabase
import com.example.mycontacts.data.source.room.entity.ContactData
import com.example.mycontacts.utils.SingleBlock
import kotlinx.android.synthetic.main.edit_item_dialog.view.*
import java.util.concurrent.Executors

class ContactDialog(context: Context, actionName: String) : AlertDialog(context) {
    private val executor = Executors.newSingleThreadExecutor()
    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.edit_item_dialog, null, false)
    private var listener: SingleBlock<ContactData>? = null
    private var contactData: ContactData? = null

    init {
        setView(contentView)
        setButton(BUTTON_POSITIVE, actionName) { _, _ ->
            val data = contactData ?: ContactData()
            data.name = contentView.re_name_dialog.text.toString()
            data.email = contentView.re_surname_dialog.text.toString()
            val temp = ContactData(
                name = data.name,
                email = data.email,
                userId = LocalStorage.instance.activeUser.id
            )
            runOnWorkerThread {
                AppDatabase.getDatabase(App.instance).contactDao().insert(temp)
            }
            listener?.invoke(data)
        }
        setButton(BUTTON_NEGATIVE, "Cancel") { _, _ -> }
    }

    fun setStudentData(contactData: ContactData) = with(contentView) {
        this@ContactDialog.contactData = contactData
        re_name_dialog.setText(contactData.name)
        re_surname_dialog.setText(contactData.email)
    }

    fun setOnClickListener(block: SingleBlock<ContactData>) {
        listener = block
    }
    private fun runOnWorkerThread(f: () -> Unit) {
        executor.execute { f() }
    }
}