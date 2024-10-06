package com.example.carpartsshop.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding is null")

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()

        binding.viewPager.isUserInputEnabled = true

        binding.viewPager.adapter = ViewPagerAdapter(this)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> navigateToPage(0)
                R.id.cart -> navigateToPage(1)
                R.id.account -> navigateToPage(2)
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNavigationView.selectedItemId = R.id.home
                    1 -> binding.bottomNavigationView.selectedItemId = R.id.cart
                    2 -> binding.bottomNavigationView.selectedItemId = R.id.account
                }
            }
        })
    }

    private fun setupViewPager() {
        viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(this)
        viewPager.isUserInputEnabled = true
    }

    private fun navigateToPage(pageIndex: Int) {
        if (binding.flContainer.visibility == View.VISIBLE) {
            binding.flContainer.visibility = View.GONE
        }
        viewPager.setCurrentItem(pageIndex, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}