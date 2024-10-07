package com.example.carpartsshop.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carpartsshop.R
import com.example.carpartsshop.ui.home.selectedCategory.SelectedCategoryItem

class CartAdapter(
    private var cartList: List<SelectedCategoryItem>,
    private val listener: RecyclerViewEvent
) : RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder>() {

    inner class CartAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        var image: ImageView = itemView.findViewById(R.id.iv_product_image)
        var title: TextView = itemView.findViewById(R.id.tv_product_title)
        var price: TextView = itemView.findViewById(R.id.tv_product_price)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position == RecyclerView.NO_POSITION) return
            listener.onItemClick(cartList[position])
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position == RecyclerView.NO_POSITION) return false

            val popupMenu = PopupMenu(v?.context, v)
            popupMenu.menuInflater.inflate(R.menu.context_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_delete -> {
                        listener.onItemDelete(cartList[position])
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
            return true
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.CartAdapterViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        return CartAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartAdapter.CartAdapterViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(cartList[position].titleImage)
            .into(holder.image)
        holder.title.text = cartList[position].name
        holder.price.text = cartList[position].price
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newProducts: List<SelectedCategoryItem>) {
        cartList = newProducts
        notifyDataSetChanged()
    }

    interface RecyclerViewEvent {
        fun onItemClick(item: SelectedCategoryItem)
        fun onItemDelete(item: SelectedCategoryItem)
    }
}
