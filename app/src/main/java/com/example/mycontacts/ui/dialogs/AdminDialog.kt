package com.example.mycontacts.ui.dialogs

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mycontacts.R
import com.example.mycontacts.data.source.room.entity.UserData
import com.example.mycontacts.utils.SingleBlock
import kotlinx.android.synthetic.main.edit_item_dialog1.view.*

class AdminDialog(context: Context, actionName:String) : AlertDialog(context) {
    private val contentView = LayoutInflater.from(context).inflate(R.layout.edit_item_dialog1, null, false)
    private var listener: SingleBlock<UserData>? = null
    private var userData: UserData? = null

    init {
        setView(contentView)
        setButton(BUTTON_POSITIVE, actionName) { _, _ ->
            val data = userData ?: UserData()
            data.fullName = contentView.re_full_name_dialog.text.toString()
            data.login = contentView.re_name_dialog.text.toString()
            data.password = contentView.re_surname_dialog.text.toString()
            listener?.invoke(data)
        }
        setButton(BUTTON_NEGATIVE, "Cancel" ){_,_->}
    }

    fun setStudentData(userData: UserData) = with(contentView) {
        this@AdminDialog.userData = userData
        re_full_name_dialog.hint = "Full name: ${userData.fullName}"
        re_name_dialog.hint = "Login: ${userData.login}"
        re_surname_dialog.hint = "Password: ${userData.password}"
    }

    fun setOnClickListener(block: SingleBlock<UserData>) {
        listener = block
    }
}