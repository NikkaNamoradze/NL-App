package com.example.nlapp.ui.admin

import android.util.Log.d
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nlapp.adapters.AdminAdapter
import com.example.nlapp.databinding.ActivityMainBinding.bind
import com.example.nlapp.databinding.ActivityMainBinding.inflate
import com.example.nlapp.databinding.AdminFragmentBinding
import com.example.nlapp.model.User
import com.example.nlapp.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AdminFragment : BaseFragment<AdminFragmentBinding>(AdminFragmentBinding::inflate) {

    private val viewModel: AdminViewModel by viewModels()

    private val adminAdapter by lazy {
        AdminAdapter()
    }


    override fun start() {
        setUpRecycler()
        listeners()
    }

    private fun setUpRecycler() {

        binding.rvAdminRecycler.layoutManager = LinearLayoutManager(context)
        binding.rvAdminRecycler.adapter = adminAdapter
        viewModel.getAdminData()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.adminFlow.collect {
                    adminAdapter.setData(it)
                }
            }
        }
    }

    private fun listeners() {
        adminAdapter.adminItemClicked = {
            viewModel.deleteUser(viewModel.auth.currentUser?.uid!!)
        }
    }
}