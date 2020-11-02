package com.example.gelderlandplein.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.adapters.EventAdapter
import com.example.gelderlandplein.dummy.Art
import com.example.gelderlandplein.dummy.Event
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.android.synthetic.main.item_event_list_content.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        for (i in Event.titles.indices){
            events.add(Event(Event.titles[i], Event.images[i], null))
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
    }

    override fun onCardViewClick(dummyEvent: Event, position: Int) {
        goToDetail(dummyEvent)
    }

    private fun goToDetail(event: Event){
        setFragmentResult(REQ_EVENT_KEY, bundleOf(Pair(BUNDLE_EVENT_KEY, Event(event.title, event.image, tv_event_extrainfo.text.toString()))))
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