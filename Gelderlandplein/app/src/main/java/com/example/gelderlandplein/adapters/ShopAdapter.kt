package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R

class ShopAdapter() : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false))
    }

    override fun onBindViewHolder(holder: ShopAdapter.ViewHolder, position: Int) {
        //TODO
    }

    override fun getItemCount(): Int {
        //TODO
        return 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //TODO
    }
}