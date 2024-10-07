package com.example.carpartsshop.repository

import android.util.Log
import com.example.carpartsshop.ui.home.selectedCategory.SelectedCategoryItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseDatabaseManager private constructor() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Items")

    var products = mutableListOf<SelectedCategoryItem>()

    init {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                products.clear()
                for (snapshot in dataSnapshot.children) {
                    val item = snapshot.getValue(SelectedCategoryItem::class.java)
                    if (item != null) {
                        products.add(item)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseDatabaseManager", "onCancelled вызван")
                Log.e("FirebaseDatabaseManager", "Ошибка базы данных: ${databaseError.message}")
                Log.e("FirebaseDatabaseManager", "Код ошибки: ${databaseError.code}")
                Log.e("FirebaseDatabaseManager", "Детали ошибки: ${databaseError.details}")
            }
        })
    }

    fun getItems(): List<SelectedCategoryItem> {
        return products
    }

    companion object {
        @Volatile
        private var instance: FirebaseDatabaseManager? = null

        fun getInstance(): FirebaseDatabaseManager {
            return instance ?: synchronized(this) {
                instance ?: FirebaseDatabaseManager().also { instance = it }
            }
        }
    }
}
