package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R
import com.example.gelderlandplein.dummy.Art
import kotlinx.android.synthetic.main.item_art_list_content.view.*

class ArtAdapter(private val arts: List<Art>, private val onClick: OnCardViewClickListener
) : RecyclerView.Adapter<ArtAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_art_list_content, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(arts[position], onClick)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun dataBind(art: Art, action: OnCardViewClickListener){
            itemView.tv_art_title.text = art.title
            itemView.iv_art.setImageResource(art.image)

            itemView.setOnClickListener {
                action.onCardViewClick(art, adapterPosition)
            }
        }
    }

    interface OnCardViewClickListener {
        fun onCardViewClick(dummyArt: Art, position: Int)
    }
}