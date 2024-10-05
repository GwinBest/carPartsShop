package com.example.carpartsshop.ui.home.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.FragmentCategoriesBinding

class CategoriesFragment :
    Fragment(),
    CategoriesAdapter.RecyclerViewEvent {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentCategoriesBinding is null")

    private lateinit var categories: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categories = resources.getStringArray(R.array.categories).toList()

        binding.rvCategories.layoutManager = LinearLayoutManager(context)
        binding.rvCategories.adapter = CategoriesAdapter(categories, this)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onItemClick(item: String) {
        val action = CategoriesFragmentDirections.
        actionCategoriesFragmentToSelectedCategoryFragment(item)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}