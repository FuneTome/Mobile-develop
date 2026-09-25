package com.example.gureevkurilenko

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private var users: List<User> = emptyList(),
    private val onClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(list: List<User>) {
        users = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.findViewById<TextView>(R.id.textFullName).text = user.fullName
            itemView.findViewById<TextView>(R.id.textLogin).text = user.login

            val adminMark = itemView.findViewById<TextView>(R.id.textAdminMark)
            adminMark.visibility = if (user.isAdmin) View.VISIBLE else View.GONE

            if (user.avatarUri.isNotEmpty()) {
                try {
                    itemView.findViewById<ImageView>(R.id.imageAvatar)
                        .setImageURI(Uri.parse(user.avatarUri))
                } catch (_: Exception) {}
            }

            itemView.setOnClickListener { onClick(user) }
        }
    }
}