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

class HomeArtAdapter(private val arts: List<Art>, private val onClick: (Art) -> Unit) : RecyclerView.Adapter<HomeArtAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener {
                onClick(arts[adapterPosition])
            }
        }

        fun dataBind(art: Art){
            Picasso.get().load(art.image).into(itemView.iv_item_art)
            itemView.tv_item_art_title.text = art.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_art_carousel_content, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(arts[position])
    }

    override fun getItemCount(): Int {
        return arts.size
    }
}