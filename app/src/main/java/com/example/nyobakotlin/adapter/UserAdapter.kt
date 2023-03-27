package com.example.nyobakotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nyobakotlin.R
import com.example.nyobakotlin.data.entity.User

class UserAdapter(private var List: List<User>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    private lateinit var dialog: Dialog

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    interface Dialog {
        fun onClick(position: Int)
    }

    var onItemClick : ((User) -> Unit)? = null
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val projectName: TextView = itemView.findViewById(R.id.projectName)
        val taskName: TextView = itemView.findViewById(R.id.taskName)

        init {
            itemView.setOnClickListener {
                dialog.onClick(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = List[position]
        holder.projectName.text = user.projectName
        holder.taskName.text = user.taskName
    }

    override fun getItemCount(): Int {
        return List.size
    }
}
