package com.example.carpartsshop.ui.account

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.FragmentNavhostAccountBinding
import com.example.carpartsshop.ui.BaseDataBindingFragment

class AccountNavHostFragment : BaseDataBindingFragment<FragmentNavhostAccountBinding>() {
    override fun getLayoutRes(): Int = R.layout.fragment_navhost_account

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(
            R.id.nested_nav_host_fragment_account) as NavHostFragment
        val navController = navHostFragment.navController
    }
}