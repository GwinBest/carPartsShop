package com.example.carpartsshop.ui.home.selectedCategory

import android.os.Parcel
import android.os.Parcelable

data class SelectedCategoryItem(
    val titleImage: String = "",
    val name: String = "",
    val price: String = "",
    val type: ProductType = ProductType.ALL,
    val value1: String = "",
    val value2: String = "",
    val value3: String = "",
    val value4: String = "",
    val value5: String = "",
    val value6: String = "",
    val value7: String = "",
    val value8: String = ""
) : Parcelable {

    var isInFavorites = false
    var isInCart = false

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        ProductType.valueOf(parcel.readString() ?: ProductType.ALL.name),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titleImage)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(type.name)
        parcel.writeString(value1)
        parcel.writeString(value2)
        parcel.writeString(value3)
        parcel.writeString(value4)
        parcel.writeString(value5)
        parcel.writeString(value6)
        parcel.writeString(value7)
        parcel.writeString(value8)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SelectedCategoryItem> {
        override fun createFromParcel(parcel: Parcel): SelectedCategoryItem {
            return SelectedCategoryItem(parcel)
        }

        override fun newArray(size: Int): Array<SelectedCategoryItem?> {
            return arrayOfNulls(size)
        }
    }
}
