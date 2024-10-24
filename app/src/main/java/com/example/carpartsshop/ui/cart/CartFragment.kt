package com.example.carpartsshop.ui.cart

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.FragmentCartBinding
import com.example.carpartsshop.ui.home.selectedCategory.SelectedCategoryItem

class CartFragment :
    Fragment(),
    CartAdapter.RecyclerViewEvent {
    private var _binding: FragmentCartBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentCartBinding is null")

    private var isSortingAscending = true

    private lateinit var cartsManager: CartManager
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartsManager = CartManager.getInstance(requireContext())

        setupRecyclerView()

        val favoritesList = cartsManager.getCart()

        binding.tvItemsInCart.text = "(${favoritesList.count()})"
        binding.tvPrice.text = favoritesList.sumOf { it.price.toInt() }.toString()

        cartAdapter =
            CartAdapter(favoritesList, this)
        binding.rvProducts.adapter = cartAdapter

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
            binding.ivSortArrowUp.setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue))
            binding.ivSortArrowDown.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray))
        } else {
            binding.ivSortArrowUp.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray))
            binding.ivSortArrowDown.setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue))
        }

        val favoritesList = cartsManager.getCart()

        val sortedProducts = if (isSortingAscending) {
            favoritesList.sortedBy { it.price }
        } else {
            favoritesList.sortedByDescending { it.price }
        }

        cartAdapter.updateData(sortedProducts)

        isSortingAscending = !isSortingAscending
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        val favoritesList = cartsManager.getCart()

        binding.tvItemsInCart.text = "(${favoritesList.count()})"
        binding.tvPrice.text = favoritesList.sumOf { it.price.toInt() }.toString()

        if (favoritesList.isEmpty()) {
            binding.rvProducts.visibility = View.GONE
            binding.tvNoRecords.visibility = View.VISIBLE
        } else {
            binding.rvProducts.visibility = View.VISIBLE
            binding.tvNoRecords.visibility = View.GONE
        }

        cartAdapter.updateData(favoritesList)
    }

    override fun onItemClick(item: SelectedCategoryItem) {
        val action = CartFragmentDirections.actionCartFragmentToProductDescription(item)
        findNavController().navigate(action)
    }

    class NativeLib {
        companion object {
            init {
                System.loadLibrary("native-lib")
            }
        }
    }

    external fun stringFromJNI(prices: IntArray): String

    @SuppressLint("SetTextI18n")
    override fun onItemDelete(item: SelectedCategoryItem) {
        val sharedPreferences = requireContext().getSharedPreferences(
            "SHARED_ITEM_CART_${item.name}", Context.MODE_PRIVATE
        )

        cartsManager.removeFromCart(item)
        item.isInCart = false
        sharedPreferences.edit().putBoolean("ITEM_CART_STATE", item.isInCart).apply()

        val favoritesList = cartsManager.getCart()
        cartAdapter.updateData(favoritesList)

        val prices = favoritesList.map { it.price.toInt() }.toIntArray()

        binding.tvItemsInCart.text = "(${favoritesList.count()})"
        binding.tvPrice.text = CartFragment().stringFromJNI(prices)

        if (favoritesList.isEmpty()) {
            binding.rvProducts.visibility = View.GONE
            binding.tvNoRecords.visibility = View.VISIBLE
        } else {
            binding.rvProducts.visibility = View.VISIBLE
            binding.tvNoRecords.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
