package com.example.carpartsshop.ui.home.selectedCategory

import android.os.Parcel
import android.os.Parcelable

data class SelectedCategoryItem(
    val titleImage : Int,
    val name: String,
    val price: Int,
    val type: ProductType
) : Parcelable {

    var isInFavorites = false
    var isInCart = false

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        ProductType.valueOf(parcel.readString() ?: "")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(titleImage)
        parcel.writeString(name)
        parcel.writeInt(price)
        parcel.writeString(type.name)
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