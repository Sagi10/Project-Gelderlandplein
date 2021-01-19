package com.example.gelderlandplein.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.adapters.EventAdapter
import com.example.gelderlandplein.models.Event
import com.example.gelderlandplein.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_art_list.*
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.android.synthetic.main.item_event_list_content.*

class EventFragment : Fragment() {
    private val events = arrayListOf<Event>()
    private val eventAdapter = EventAdapter(events, ::goToDetailEvent)

    private val firebaseViewModel : FirebaseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (events.isNotEmpty()) {
            pb_loading_event.isVisible = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        observeEvents()
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_events_list.adapter = eventAdapter
    }

    private fun observeEvents() {
        firebaseViewModel.events.observe(viewLifecycleOwner, {
            this@EventFragment.events.clear()
            this@EventFragment.events.addAll(it)
            pb_loading_event.isVisible = false
            eventAdapter.notifyDataSetChanged()
        })
    }

    private fun goToDetailEvent(event: Event) {
        firebaseViewModel.sendDetailEvent(event)
        findNavController().navigate(R.id.action_EventFragment_to_eventDetailFragment)
    }
}