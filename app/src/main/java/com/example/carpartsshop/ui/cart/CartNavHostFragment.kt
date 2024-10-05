package com.example.carpartsshop.ui.cart

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.FragmentNavhostCartBinding
import com.example.carpartsshop.ui.BaseDataBindingFragment

class CartNavHostFragment : BaseDataBindingFragment<FragmentNavhostCartBinding>() {
    override fun getLayoutRes(): Int = R.layout.fragment_navhost_cart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(
            R.id.nested_nav_host_fragment_cart) as NavHostFragment
        val navController = navHostFragment.navController
    }
}