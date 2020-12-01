package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R
import com.example.gelderlandplein.models.Shop
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_search_list.*
import kotlinx.android.synthetic.main.item_shop_list_content.view.*
import java.util.*
import kotlin.collections.ArrayList

class ShopAdapter(private val shops: ArrayList<Shop>, private val onClick: (Shop) -> Unit) :
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop_list_content, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(shops[position])
    }

    override fun getItemCount(): Int {
        return shops.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onClick(shops[adapterPosition])
            }
        }

        fun dataBind(shop: Shop) {
            Picasso.get().load(shop.image).into(itemView.iv_shop)
            itemView.tv_shop_title.text = shop.name
            itemView.tv_shop_tag.text = shop.tag
        }
    }
}