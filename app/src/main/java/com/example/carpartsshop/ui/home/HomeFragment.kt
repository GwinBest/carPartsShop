package com.example.carpartsshop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentHomeBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCategories.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_CategoriesFragment)
        }

        binding.btnFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_FavoritesFragment)
        }

        binding.btnGifts.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_GiftsFragment)
        }

        binding.btnBestSelling.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_BestSellingFragment)
        }

        binding.flFirstOnSaleItem.setOnClickListener {
            val action = HomeFragmentDirections.
            actionHomeFragmentToSelectedCategoryFragment(binding.tvFirstOnSaleTitle.text.toString())
            findNavController().navigate(action)
        }

        binding.flSecondOnSaleItem.setOnClickListener {
            val action = HomeFragmentDirections.
            actionHomeFragmentToSelectedCategoryFragment(binding.tvFirstOnSaleTitle.text.toString())
            findNavController().navigate(action)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}