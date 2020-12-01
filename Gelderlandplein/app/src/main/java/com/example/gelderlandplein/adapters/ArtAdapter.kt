package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R
import com.example.gelderlandplein.models.Art
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_art_list_content.view.*

/**
 * This adapter is using dummy data
 */
class ArtAdapter(private val arts: List<Art>, private val onClick: (Art) -> Unit )
    : RecyclerView.Adapter<ArtAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_art_list_content, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(arts[position])
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onClick(arts[adapterPosition])
            }
        }

        fun dataBind(art: Art){
            itemView.tv_art_title.text = art.name
            Picasso.get().load(art.image).into(itemView.iv_art)
            itemView.tv_art_beschrijving.text = art.beschrijving
        }
    }
}