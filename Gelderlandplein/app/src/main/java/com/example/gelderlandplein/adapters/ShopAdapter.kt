package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R
import com.example.gelderlandplein.models.Shop
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_shop_list_content.view.*

class ShopAdapter(private val shops: List<Shop>) : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_shop_list_content, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(shops[position])
    }

    override fun getItemCount(): Int {
        return shops.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun dataBind(shop: Shop){
            Picasso.get().load(shop.image).into(itemView.iv_shop)
            itemView.tv_shop_title.text = shop.name
            itemView.tv_shop_tag.text = shop.tag
        }
    }
}