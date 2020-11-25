package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R
import com.example.gelderlandplein.models.Art
import com.example.gelderlandplein.models.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_art_carousel_content.view.*

class HomeArtAdapter(private val arts: List<Art>,private val onClick: OnArtCardViewClickListener) : RecyclerView.Adapter<HomeArtAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun dataBind(art: Art, action: OnArtCardViewClickListener){
            Picasso.get().load(art.image).into(itemView.iv_item_art)
            itemView.tv_item_art_title.text = art.name
            itemView.setOnClickListener {
                action.onArtCardViewClick(art, adapterPosition)
            }
        }
    }

    interface OnArtCardViewClickListener {
        fun onArtCardViewClick(art: Art, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_art_carousel_content, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(arts[position], onClick)
    }

    override fun getItemCount(): Int {
        return arts.size
    }
}