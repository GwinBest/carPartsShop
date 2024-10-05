package com.example.carpartsshop.ui.home.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.FragmentFavoritesBinding
import com.example.carpartsshop.ui.home.selectedCategory.SelectedCategoryFragmentDirections
import com.example.carpartsshop.ui.home.selectedCategory.SelectedCategoryItem

class FavoritesFragment :
    Fragment(),
    FavoritesAdapter.RecyclerViewEvent {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentFavoritesBinding is null")

    private var isSortingAscending = true

    private lateinit var favoritesManager: FavoritesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesManager = FavoritesManager.getInstance(requireContext())

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_FavoritesFragment_to_HomeFragment)
        }

        setupRecyclerView()

        binding.flSortByPrice.setOnClickListener {
            handleSortClick()
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(context, 2)
        binding.rvProducts.layoutManager = layoutManager

        binding.rvProducts.post {
            val spanCount = calculateSpanCount()
            layoutManager.spanCount = spanCount
            binding.rvProducts.requestLayout()
        }

        binding.rvProducts.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            val newSpanCount = calculateSpanCount()
            if (newSpanCount != layoutManager.spanCount) {
                layoutManager.spanCount = newSpanCount
                binding.rvProducts.requestLayout()
            }
        }
    }

    private fun calculateSpanCount(): Int {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_product, null)
        itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val itemWidth = itemView.measuredWidth

        val recyclerViewWidth = binding.rvProducts.width

        val spanCount = recyclerViewWidth / itemWidth

        return spanCount.coerceIn(1, 4)
    }

    private fun handleSortClick() {
        if (isSortingAscending) {
            binding.ivSortArrowUp.apply {
                setColorFilter(ContextCompat.getColor(context, R.color.blue))
            }
            binding.ivSortArrowDown.apply {
                setColorFilter(ContextCompat.getColor(context, R.color.gray))
            }
        } else {
            binding.ivSortArrowUp.apply {
                setColorFilter(ContextCompat.getColor(context, R.color.gray))
            }
            binding.ivSortArrowDown.apply {
                setColorFilter(ContextCompat.getColor(context, R.color.blue))
            }
        }

        val favoritesList = favoritesManager.getFavoritesList()

        val sortedProducts = if (isSortingAscending) {
            favoritesList.sortedBy { it.price }
        } else {
            favoritesList.sortedByDescending { it.price }
        }

        (binding.rvProducts.adapter as? FavoritesAdapter)?.updateData(sortedProducts)
            ?: run {
                binding.rvProducts.adapter = FavoritesAdapter(sortedProducts, this)
            }

        isSortingAscending = !isSortingAscending
    }

    override fun onResume() {
        super.onResume()

        val favoritesList = favoritesManager.getFavoritesList()

        if (favoritesList.isEmpty()) {
            binding.rvProducts.visibility = View.GONE
            binding.tvNoRecords.visibility = View.VISIBLE
        } else {
            binding.rvProducts.visibility = View.VISIBLE
            binding.tvNoRecords.visibility = View.GONE
        }

        (binding.rvProducts.adapter as? FavoritesAdapter)?.updateData(favoritesList)
            ?: run {
                binding.rvProducts.adapter = FavoritesAdapter(favoritesList, this)
            }
    }

    override fun onItemClick(item: SelectedCategoryItem) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToProductDescription(item)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}