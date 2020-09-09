package com.javierrebollo.basearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javierrebollo.basearch.databinding.ItemUserBinding
import com.javierrebollo.basearch.domain.entity.User

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    var userList: List<User>? = null

    class UserViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        private var binding: ItemUserBinding? = binding

        fun bind(user: User?) {
            binding?.user = user
            binding?.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: ItemUserBinding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return UserViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        userList?.get(position)?.let {
            holder.bind(it)
        }
    }
}