package com.example.carpartsshop.ui.cart

import android.content.Context
import android.content.SharedPreferences
import com.example.carpartsshop.ui.home.selectedCategory.SelectedCategoryItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE)

    private var cartList: MutableList<SelectedCategoryItem> = mutableListOf()

    fun addToCart(item: SelectedCategoryItem) {
        cartList.add(item)
        saveCart()
    }

    fun removeFromCart(item: SelectedCategoryItem) {
        cartList.remove(item)
        saveCart()
    }

    fun getCart(): List<SelectedCategoryItem> {
        return loadCart().toList()
    }

    private fun loadCart(): MutableList<SelectedCategoryItem> {
        val favoritesJson = sharedPreferences.getString("favorites", "")
        return if (favoritesJson.isNullOrEmpty()) {
            mutableListOf()
        } else {
            Gson().fromJson(favoritesJson, object : TypeToken<MutableList<SelectedCategoryItem>>() {}.type)
        }
    }

    private fun saveCart() {
        val favoritesJson = Gson().toJson(cartList)
        sharedPreferences.edit().putString("favorites", favoritesJson).apply()
    }

    companion object {
        @Volatile
        private var instance: CartManager? = null

        fun getInstance(context: Context): CartManager =
            instance ?: synchronized(this) {
                instance ?: CartManager(context).also { instance = it }
            }
    }
}
