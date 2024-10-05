package com.example.carpartsshop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.isUserInputEnabled = false

        binding.viewPager.adapter = ViewPagerAdapter(this)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    binding.viewPager.setCurrentItem(0, false)
                    true
                }

                R.id.cart -> {
                    binding.viewPager.setCurrentItem(1, false)
                    true
                }
                R.id.account -> {
                    binding.viewPager.setCurrentItem(2, false)
                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}