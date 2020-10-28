package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R
import com.example.gelderlandplein.dummy.Art
import com.example.gelderlandplein.ui.art.ArtFragment
import kotlinx.android.synthetic.main.item_art_list_content.view.*

/**
 * This adapter is using dummy data
 */
class ArtAdapter(private val arts: List<Art>, private val onArtClick: OnArtCardViewClickListener
) : RecyclerView.Adapter<ArtAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_art_list_content, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(arts[position], onArtClick)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun dataBind(art: Art, action: OnArtCardViewClickListener){
            itemView.tv_art_title.text = art.title
            itemView.iv_art.setImageResource(art.image)

            itemView.setOnClickListener {
                action.onCardViewClick(art, adapterPosition)
            }
        }
    }

    interface OnArtCardViewClickListener {
        fun onCardViewClick(dummyArt: Art, position: Int)
    }
}