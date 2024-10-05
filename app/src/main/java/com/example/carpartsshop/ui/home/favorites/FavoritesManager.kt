package com.example.carpartsshop.ui.home.favorites

import android.content.Context
import android.content.SharedPreferences
import com.example.carpartsshop.ui.home.selectedCategory.SelectedCategoryItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesManager private constructor(context: Context) {
    private val sharedPrefKey = "FavoritesPrefs"
    private val sharedPrefStringKey = "FavoritesString"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)

    private var favoritesList: MutableList<SelectedCategoryItem> = mutableListOf()

    fun addToFavorites(item: SelectedCategoryItem) {
        favoritesList.add(item)
        saveFavoritesList()
    }

    fun removeFromFavorites(item: SelectedCategoryItem) {
        favoritesList.remove(item)
        saveFavoritesList()
    }

    fun getFavoritesList(): List<SelectedCategoryItem> {
        return loadFavoritesList().toList()
    }

    private fun loadFavoritesList(): MutableList<SelectedCategoryItem> {
        val favoritesJson = sharedPreferences.getString(sharedPrefStringKey, "")
        return if (favoritesJson.isNullOrEmpty()) {
            mutableListOf()
        } else {
            Gson().fromJson(favoritesJson, object :
                TypeToken<MutableList<SelectedCategoryItem>>() {}.type)
        }
    }

    private fun saveFavoritesList() {
        val favoritesJson = Gson().toJson(favoritesList)
        sharedPreferences.edit().putString(sharedPrefStringKey, favoritesJson).apply()
    }

    companion object {
        @Volatile
        private var instance: FavoritesManager? = null

        fun getInstance(context: Context): FavoritesManager =
            instance ?: synchronized(this) {
                instance ?: FavoritesManager(context).also { instance = it }
            }
    }
}
