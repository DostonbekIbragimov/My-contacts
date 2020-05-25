package com.example.mycontacts.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.mycontacts.R
import com.example.mycontacts.data.source.room.entity.UserData
import com.example.mycontacts.utils.SingleBlock
import kotlinx.android.synthetic.main.item_admin.view.*
import com.example.mycontacts.utils.extensions.bindItem
import com.example.mycontacts.utils.extensions.inflate

class AdminAdapter : RecyclerView.Adapter<AdminAdapter.ViewHolder>() {
    private val ls = ArrayList<UserData>()
    private var listenerItem: SingleBlock<UserData>? = null
    private var listenerEdit: SingleBlock<UserData>? = null
    private var listenerDelete: SingleBlock<UserData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_admin))

    override fun getItemCount(): Int = ls.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    fun submitList(data: List<UserData>) {
        ls.clear()
        ls.addAll(data)
        notifyItemRangeRemoved(0, data.size)
    }

    fun removeItem(data: UserData) {
        val index = ls.indexOfFirst { it.id == data.id }
        ls.removeAt(index)
        notifyItemRemoved(index)
    }

    fun updateItem(data: UserData) {
        val index = ls.indexOfFirst { it.id == data.id }
        ls[index] = data
        notifyItemChanged(index)
    }

    fun insertItem(data: UserData) {
        ls.add(data)
        notifyItemInserted(ls.size - 1)
    }

    fun setOnItemClickListener(block: SingleBlock<UserData>) {
        listenerItem = block
    }

    fun setOnItemEditListener(block: SingleBlock<UserData>) {
        listenerEdit = block
    }

    fun setOnItemDeleteListener(block: SingleBlock<UserData>) {
        listenerDelete = block
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.apply {
                setOnClickListener { listenerItem?.invoke(ls[adapterPosition]) }
                buttonMore.setOnClickListener { it ->
                    val menu = PopupMenu(context, it)
                    menu.inflate(R.menu.menu_more)
                    menu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menuDelete -> listenerDelete?.invoke(ls[adapterPosition])
                            R.id.menuEdit -> listenerEdit?.invoke(ls[adapterPosition])
                        }
                        true
                    }
                    menu.show()
                }
            }
        }

        fun bind() = bindItem {
            val d = ls[adapterPosition]
            textTitle.text = "Full name: ${d.fullName}"
            textInfo.text = "Login: ${d.login}"
            textPassword.text = "Password: ${d.password}"
        }
    }
}