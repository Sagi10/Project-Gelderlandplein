package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R
import com.example.gelderlandplein.models.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event_carousel_content.view.*

class HomeEventAdapter(private val events: List<Event>, private val onClick: OnEventCardViewClickListener) : RecyclerView.Adapter<HomeEventAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun dataBind(event: Event, action: OnEventCardViewClickListener){
            Picasso.get().load(event.image).into(itemView.iv_item_event)
            itemView.tv_item_event_title.text = event.title
            itemView.tv_item_event_info.text = event.beschrijving
        }
    }

    interface OnEventCardViewClickListener {
        fun onEventCardViewClick(event: Event, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event_carousel_content, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.dataBind(events[position], onClick)
    }

    override fun getItemCount(): Int {
        return events.size
    }


}