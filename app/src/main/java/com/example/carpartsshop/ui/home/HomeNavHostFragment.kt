package com.example.carpartsshop.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.FragmentNavhostHomeBinding
import com.example.carpartsshop.ui.BaseDataBindingFragment

class HomeNavHostFragment: BaseDataBindingFragment<FragmentNavhostHomeBinding>() {
    override fun getLayoutRes(): Int = R.layout.fragment_navhost_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(
            R.id.nested_nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
    }
}