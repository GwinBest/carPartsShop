package com.example.carpartsshop.ui.home.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carpartsshop.R

class CategoriesAdapter(
    private val titles: List<String>,
    private val listener: RecyclerViewEvent
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>()  {
    inner class CategoriesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var title: TextView = itemView.findViewById(R.id.tv_title)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position =  adapterPosition
            if (position == RecyclerView.NO_POSITION) return
            listener.onItemClick(titles[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        return CategoriesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return titles.size;
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.title.text = titles[position]
    }

    interface RecyclerViewEvent {
        fun onItemClick(item: String)
    }
}