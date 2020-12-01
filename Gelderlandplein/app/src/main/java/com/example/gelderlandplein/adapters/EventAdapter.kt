package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R
import com.example.gelderlandplein.models.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_art_list_content.view.*
import kotlinx.android.synthetic.main.item_event_list_content.view.*

/**
 * This adapter is using dummy data
 */
class EventAdapter(private val events: List<Event>, private val onClick: (Event) -> Unit)
    : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event_list_content, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener {
                onClick(events[adapterPosition])
            }
        }

        fun dataBind(event: Event){
            Picasso.get().load(event.image).into(itemView.iv_event)
            itemView.tv_event_title.text = event.title
            itemView.tv_event_beschrijving.text = event.beschrijving
        }
    }
}