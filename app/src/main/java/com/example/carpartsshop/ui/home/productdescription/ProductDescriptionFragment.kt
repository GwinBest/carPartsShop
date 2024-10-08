package com.example.carpartsshop.ui.home.productdescription

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.carpartsshop.R
import com.example.carpartsshop.databinding.FragmentProductDescriptionBinding
import com.example.carpartsshop.ui.cart.CartManager
import com.example.carpartsshop.ui.home.favorites.FavoritesManager
import com.example.carpartsshop.ui.home.selectedCategory.ProductType
import com.example.carpartsshop.ui.home.selectedCategory.SelectedCategoryItem

class ProductDescriptionFragment : Fragment() {
    private var _binding: FragmentProductDescriptionBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentProductDescriptionBinding is null")

    private lateinit var favoritesManager: FavoritesManager
    private val args: ProductDescriptionFragmentArgs by navArgs()
    private lateinit var selectedItem: SelectedCategoryItem

    private lateinit var cartManager: CartManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartManager = CartManager.getInstance(requireContext())

        favoritesManager = FavoritesManager.getInstance(requireContext())
        selectedItem = args.selectedCategoryItem

        val sharedPreferencesFav = requireContext().getSharedPreferences(
            "SHARED_ITEM_FAV_${selectedItem.name}", Context.MODE_PRIVATE
        )
        selectedItem.isInFavorites = sharedPreferencesFav.getBoolean("ITEM_FAV_STATE", false)

        val sharedPreferencesCart = requireContext().getSharedPreferences(
            "SHARED_ITEM_CART_${selectedItem.name}", Context.MODE_PRIVATE
        )
        selectedItem.isInCart = sharedPreferencesCart.getBoolean("ITEM_CART_STATE", false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDescriptionBinding.inflate(
            inflater, container, false)

        setupUI()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupUI() {
        setCartButtonColorAndImage(selectedItem)
        setFavoritesButtonColorAndImage(selectedItem)

        Glide.with(this)
            .load(selectedItem.titleImage)
            .into(binding.ivProductImage)

        binding.tvProductName.text = selectedItem.name

        binding.tvValue1.text = selectedItem.value1
        binding.tvValue2.text = selectedItem.value2
        binding.tvValue3.text = selectedItem.value3
        binding.tvValue4.text = selectedItem.value5
        binding.tvValue5.text = selectedItem.value6
        binding.tvValue6.text = selectedItem.value7
        binding.tvValue7.text = selectedItem.value8

        val arrayId: Int? = when (selectedItem.type.toString()) {
            ProductType.TIRES.toString() -> R.array.tires_description
            ProductType.BRAKE_DISCS.toString() -> R.array.brake_discs_description
            ProductType.PADS.toString() -> R.array.pads_description
            ProductType.CHASSIS.toString() -> R.array.chassis_description
            ProductType.ENGINE.toString() -> R.array.engine_description
            else -> null
        }

        arrayId?.let { id ->
            val descriptions = resources.getStringArray(id)
            for (i in descriptions.indices) {
                val fieldName = "tvCategory${i + 1}"
                val field = binding::class.java.getDeclaredField(fieldName)
                field.isAccessible = true
                val textView = field.get(binding) as TextView
                textView.text = descriptions.getOrNull(i) ?: ""
            }
        }

        binding.btnAddToCart.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences(
                "SHARED_ITEM_CART_${selectedItem.name}", Context.MODE_PRIVATE
            )

            if (!selectedItem.isInCart) {
                selectedItem.let { item -> cartManager.addToCart(item) }
                selectedItem.isInCart = true

                setCartButtonColorAndImage(selectedItem)
            } else {
                selectedItem.let { item -> cartManager.removeFromCart(item) }
                selectedItem.isInCart = false

                setCartButtonColorAndImage(selectedItem)
            }

            selectedItem.isInCart.let { isInFavorites ->
                sharedPreferences.edit().putBoolean("ITEM_CART_STATE", isInFavorites).apply()
            }
        }

        binding.btnFavorites.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences(
                "SHARED_ITEM_FAV_${selectedItem.name}", Context.MODE_PRIVATE
            )

            if (!selectedItem.isInFavorites) {
                selectedItem.let { item -> favoritesManager.addToFavorites(item) }
                selectedItem.isInFavorites = true
                setFavoritesButtonColorAndImage(selectedItem)
            } else {
                selectedItem.let { item -> favoritesManager.removeFromFavorites(item) }
                selectedItem.isInFavorites = false
                setFavoritesButtonColorAndImage(selectedItem)
            }

            selectedItem.isInFavorites.let { isInFavorites ->
                sharedPreferences.edit().putBoolean("ITEM_FAV_STATE", isInFavorites).apply()
            }
        }
    }

    private fun setCartButtonColorAndImage(selectedItem: SelectedCategoryItem?) {
        if (selectedItem?.isInCart == true) {
            binding.btnAddToCart.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.red)
            )
            binding.btnAddToCart.text = "Remove from cart"
        } else {
            binding.btnAddToCart.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.purple)
            )
            binding.btnAddToCart.text = "Add to cart"
        }
    }

    private fun setFavoritesButtonColorAndImage(selectedItem: SelectedCategoryItem?) {
        if (selectedItem?.isInFavorites == true) {
            binding.btnFavorites.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.red)
            )
            binding.btnFavorites.setImageResource(R.drawable.ic_remove_from_favorites)
        } else {
            binding.btnFavorites.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.purple)
            )
            binding.btnFavorites.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(item: SelectedCategoryItem) =
            ProductDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("EXTRA_KEY_ITEM", item)
                }
            }
    }
}