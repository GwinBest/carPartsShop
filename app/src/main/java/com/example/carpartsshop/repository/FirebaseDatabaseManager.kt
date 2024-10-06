//package com.example.carpartsshop.repository
//
//import com.example.carpartsshop.ui.home.selectedCategory.SelectedCategoryItem
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//class FirebaseDatabaseManager {
//    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
//
//    fun getItems(callback: (List<SelectedCategoryItem>) -> Unit) {
//        database.child("Items").addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val items = mutableListOf<SelectedCategoryItem>()
//                for (snapshot in dataSnapshot.children) {
//                    val item = snapshot.getValue(SelectedCategoryItem::class.java)
//                    if (item != null) {
//                        items.add(item)
//                    }
//                }
//                callback(items)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }
//}
