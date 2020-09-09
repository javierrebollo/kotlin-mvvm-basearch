package com.javierrebollo.basearch.ui.screens.userlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.javierrebollo.basearch.DependencyInjector
import com.javierrebollo.basearch.base.BaseFragment
import com.javierrebollo.basearch.base.ErrorType
import com.javierrebollo.basearch.databinding.FragmentUserListBinding
import com.javierrebollo.basearch.ui.adapter.UsersAdapter


class UserListFragment : BaseFragment<FragmentUserListBinding, UserListVM, UserListVMFactory>() {
    override val viewModel: UserListVM by viewModels {
        buildViewModelFactory()
    }

    private val userAdapter = UsersAdapter()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentUserListBinding = FragmentUserListBinding.inflate(inflater, container, false)

    override fun initComponents() {
        binding.viewModel = viewModel
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    override fun addListeners() {
    }

    override fun addObservers() {
        viewModel.userList.observe(viewLifecycleOwner) {
            userAdapter.userList = it
            userAdapter.notifyDataSetChanged()

            it.forEach { user ->
                Log.d(TAG, user.toString())
            }
        }
    }

    override fun errorHandler(errorType: ErrorType) {
        when (errorType) {
            is ErrorType.LoginFail -> Toast.makeText(context, errorType.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun buildViewModelFactory(): UserListVMFactory = DependencyInjector.provideUserListVMFactory()
}