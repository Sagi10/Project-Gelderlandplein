package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R
import com.example.gelderlandplein.models.Shop
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_shop_carousel_content.view.*

class HomeShopAdapter(private val shops: List<Shop>, private val onClick: OnShopsEventClickListener) : RecyclerView.Adapter<HomeShopAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun databind(shop: Shop, action: OnShopsEventClickListener){
            Picasso.get().load(shop.image).into(itemView.iv_shop_logo)
        }
    }

    interface OnShopsEventClickListener {
        fun onShopsCardViewClick(shop: Shop, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_shop_carousel_content, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(shops[position], onClick)
    }

    override fun getItemCount(): Int {
        return shops.size
    }
}