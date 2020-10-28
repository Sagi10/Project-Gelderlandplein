package com.example.gelderlandplein.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.gelderlandplein.R
import com.example.gelderlandplein.dummy.Event
import kotlinx.android.synthetic.main.item_detail_event.*

class EventDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_detail_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEventResult()
    }

    private fun observeEventResult(){
        setFragmentResultListener(REQ_EVENT_KEY) { Key, bundle ->
            bundle.getParcelable<Event>(BUNDLE_EVENT_KEY)?.let {
                iv_detail_event.setImageResource(it.image)
                tv_detail_event_title.text = it.title
                tv_event_geldig.text = it.actieGeldig
            }
        }
    }
}