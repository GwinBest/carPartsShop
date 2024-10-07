package com.example.carpartsshop.ui.home.selectedCategory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carpartsshop.R

class SelectedCategoriesAdapter(
    private var products: List<SelectedCategoryItem>,
    private val listener: RecyclerViewEvent
): RecyclerView.Adapter<SelectedCategoriesAdapter.SelectedCategoriesViewHolder>()  {
    inner class SelectedCategoriesViewHolder(itemView: View) :
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
            listener.onItemClick(products[position])
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectedCategoriesViewHolder {
        val itemView =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return SelectedCategoriesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: SelectedCategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(products[position].titleImage)
            .into(holder.image)
            holder.title.text = products[position].name
            holder.price.text = products[position].price
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newProducts: List<SelectedCategoryItem>) {
        products = newProducts
        notifyDataSetChanged()
    }

    interface RecyclerViewEvent {
        fun onItemClick(item: SelectedCategoryItem)
    }
}