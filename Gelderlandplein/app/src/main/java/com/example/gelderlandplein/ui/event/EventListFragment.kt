package com.example.gelderlandplein.ui.event

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.adapters.EventAdapter
import com.example.gelderlandplein.models.Art
import com.example.gelderlandplein.models.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_art_list.*
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.android.synthetic.main.item_event_list_content.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

const val REQ_EVENT_KEY = "req_event"
const val BUNDLE_EVENT_KEY = "bundle_event"

/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment(), EventAdapter.OnEventCardViewClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val events = arrayListOf<Event>()
    private val eventAdapter = EventAdapter(events, this)

    private lateinit var database: DatabaseReference
    private var eventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        database = Firebase.database.reference.child("events")

        if (events.isNotEmpty()){
            pb_loading_event.isVisible = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_events_list.adapter = eventAdapter
        getAllEvents()
    }

    private fun getAllEvents(){
        this.eventListener = null
        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                events.clear()
                for (currentEvent: DataSnapshot in snapshot.children) {
                    try {
                        var event = Event(currentEvent.child("name").value.toString(), currentEvent.child("image").value.toString(), currentEvent.child("geldigheid").value.toString(), currentEvent.child("beschrijving").value.toString(), currentEvent.child("link").value.toString())
                        events.add(event)
                    }catch (exception: Exception){
                        Log.e(ContentValues.TAG, exception.toString())
                    }
                }
                eventAdapter.notifyDataSetChanged()
                pb_loading_event.isVisible = false
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, "Er gaat iets mis met het ophalen van de arts")
            }

        }
        database.addValueEventListener(eventListener)
        this.eventListener = eventListener
    }

    override fun onCardViewClick(dummyEvent: Event, position: Int) {
        goToDetail(dummyEvent)
    }

    private fun goToDetail(event: Event){
        setFragmentResult(REQ_EVENT_KEY, bundleOf(Pair(BUNDLE_EVENT_KEY, Event(event.title, event.image, event.actieGeldig, event.beschrijving, event.link))))
        findNavController().navigate(R.id.action_EventFragment_to_eventDetailFragment)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fourthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EventFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}