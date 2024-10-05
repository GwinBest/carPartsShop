package com.example.carpartsshop.ui.home.gifts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.carpartsshop.databinding.FragmentGiftsBinding
import com.example.carpartsshop.ui.home.bestselling.BestSellingFragment

class GiftsFragment : Fragment() {
    private var _binding: FragmentGiftsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentGiftsBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGiftsBinding.inflate(inflater, container, false)

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