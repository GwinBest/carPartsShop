package com.example.carpartsshop.ui.home.selectedCategory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.FragmentSelectedCategoryBinding

class SelectedCategoryFragment:
    Fragment(),
    SelectedCategoriesAdapter.RecyclerViewEvent {
    private var _binding: FragmentSelectedCategoryBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentSelectedCategoryBinding is null")

    private val args: SelectedCategoryFragmentArgs by navArgs()

    private val categoryNameBundleKey = "CATEGORY_NAME"
    private var categoryName: String? = null

    private var isSortingAscending = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectedCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryName = args.categoryName

        setupUI()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: SelectedCategoryItem) {
        val action = SelectedCategoryFragmentDirections.actionSelectedCategoryFragmentToProductDescription(item)
        findNavController().navigate(action)
    }

    private fun setupUI() {
        binding.tvTitle.text = categoryName

        val products = listOf(SelectedCategoryItem(
            titleImage = 1,
            name = "Smartphone",
            price = 500,
            type = ProductType.ENGINE
        ),
        SelectedCategoryItem(
            titleImage = 1,
            name = "T-Shirt",
            price = 20,
            type = ProductType.ENGINE
        ),
        SelectedCategoryItem(
            titleImage = 1,
            name = "Pizza",
            price = 15,
            type = ProductType.ENGINE
        ),
        SelectedCategoryItem(
            titleImage = 1,
            name = "Book",
            price = 10,
            type = ProductType.ENGINE
        ))

        val productType = categoryName?.let {
            ProductType.valueOf(it.replace(" ", "_").uppercase())
        }

        val displayProducts = filterProducts(products, productType)

        if (displayProducts.isEmpty()) {
            binding.rvProducts.visibility = View.GONE
            binding.tvNoRecords.visibility = View.VISIBLE
        } else {
            binding.rvProducts.visibility = View.VISIBLE
            binding.tvNoRecords.visibility = View.GONE

            setupRecyclerView(displayProducts)
        }

        setupSorting(displayProducts)

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun filterProducts(
        products: List<SelectedCategoryItem>,
        type: ProductType?): List<SelectedCategoryItem> {
        return if (type == ProductType.ALL) products else products.filter { it.type == type }
    }

    private fun setupRecyclerView(displayProducts: List<SelectedCategoryItem>) {
        val layoutManager = GridLayoutManager(context, 2)
        binding.rvProducts.layoutManager = layoutManager
        binding.rvProducts.adapter = SelectedCategoriesAdapter(displayProducts, this)

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

    @SuppressLint("InflateParams")
    private fun calculateSpanCount(): Int {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_product, null)
        itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val itemWidth = itemView.measuredWidth

        val recyclerViewWidth = binding.rvProducts.width

        val spanCount = recyclerViewWidth / itemWidth

        return spanCount.coerceIn(2, 4)
    }

    private fun setupSorting(displayProducts: List<SelectedCategoryItem>) {
        binding.flSortByPrice.setOnClickListener {
            if (isSortingAscending) {
                binding.ivSortArrowUp.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.blue))
                binding.ivSortArrowDown.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.gray))
            } else {
                binding.ivSortArrowUp.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.gray))
                binding.ivSortArrowDown.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.blue))
            }

            val sortedProducts = if (isSortingAscending) {
                displayProducts.sortedBy { it.price }
            } else {
                displayProducts.sortedByDescending { it.price }
            }

            (binding.rvProducts.adapter as? SelectedCategoriesAdapter)?.updateData(sortedProducts)
                ?: run {
                    binding.rvProducts.adapter = SelectedCategoriesAdapter(
                        sortedProducts, this)
                }

            isSortingAscending = !isSortingAscending
        }
    }
}