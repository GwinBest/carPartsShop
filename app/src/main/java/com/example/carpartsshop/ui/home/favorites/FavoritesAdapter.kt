package com.example.carpartsshop.ui.home.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carpartsshop.R
import com.example.carpartsshop.ui.home.selectedCategory.SelectedCategoryItem

class FavoritesAdapter(
    private var favorites: List<SelectedCategoryItem>,
    private val listener: RecyclerViewEvent
): RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder>() {
    inner class FavoritesAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var image: ImageView = itemView.findViewById(R.id.iv_product_image)
        var title: TextView = itemView.findViewById(R.id.tv_product_title)
        var price: TextView = itemView.findViewById(R.id.tv_product_price)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position =  adapterPosition
            if (position == RecyclerView.NO_POSITION) return
            listener.onItemClick(favorites[position])
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesAdapterViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        return FavoritesAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: FavoritesAdapterViewHolder, position: Int) {
        holder.title.text = favorites[position].name
        holder.price.text = favorites[position].price.toString()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newProducts: List<SelectedCategoryItem>) {
        favorites = newProducts
        notifyDataSetChanged()
    }

    interface RecyclerViewEvent {
        fun onItemClick(item: SelectedCategoryItem)
    }
}