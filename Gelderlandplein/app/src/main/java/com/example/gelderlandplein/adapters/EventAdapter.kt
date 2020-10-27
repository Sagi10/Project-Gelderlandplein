package com.example.gelderlandplein.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gelderlandplein.R

class EventAdapter() : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event_carousel_content, parent, false))
    }

    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int) {
        //TODO
    }

    override fun getItemCount(): Int {
        //TODO
        return 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //TODO
    }
}