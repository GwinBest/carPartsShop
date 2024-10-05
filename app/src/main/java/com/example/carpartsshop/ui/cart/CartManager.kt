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

    fun addToFavorites(item: SelectedCategoryItem) {
        cartList.add(item)
        saveFavoritesList()
    }

    fun removeFromFavorites(item: SelectedCategoryItem) {
        cartList.remove(item)
        saveFavoritesList()
    }

    fun getFavoritesList(): List<SelectedCategoryItem> {
        return loadFavoritesList().toList()
    }

    private fun loadFavoritesList(): MutableList<SelectedCategoryItem> {
        val favoritesJson = sharedPreferences.getString("favorites", "")
        return if (favoritesJson.isNullOrEmpty()) {
            mutableListOf()
        } else {
            Gson().fromJson(favoritesJson, object : TypeToken<MutableList<SelectedCategoryItem>>() {}.type)
        }
    }

    private fun saveFavoritesList() {
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
