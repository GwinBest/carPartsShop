package com.example.carpartsshop.ui.home.bestselling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.carpartsshop.databinding.FragmentBestSellingBinding

class BestSellingFragment : Fragment() {
    private var _binding: FragmentBestSellingBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentBestSellingBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBestSellingBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = BestSellingFragment()
    }
}