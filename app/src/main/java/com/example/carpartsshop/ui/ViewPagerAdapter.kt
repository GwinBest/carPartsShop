package com.example.carpartsshop.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.carpartsshop.ui.account.AccountNavHostFragment
import com.example.carpartsshop.ui.cart.CartNavHostFragment
import com.example.carpartsshop.ui.home.HomeNavHostFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragments = listOf(
        HomeNavHostFragment(),
        CartNavHostFragment(),
        AccountNavHostFragment(),
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}