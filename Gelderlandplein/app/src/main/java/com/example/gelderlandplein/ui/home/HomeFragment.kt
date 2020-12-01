package com.example.gelderlandplein.ui.home

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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.adapters.*
import com.example.gelderlandplein.models.Art
import com.example.gelderlandplein.models.Event
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.ui.art.BUNDLE_ART_KEY
import com.example.gelderlandplein.ui.art.REQ_ART_KEY
import com.example.gelderlandplein.ui.event.BUNDLE_EVENT_KEY
import com.example.gelderlandplein.ui.event.REQ_EVENT_KEY
import com.example.gelderlandplein.ui.search.BUNDLE_INFO_SHOP_KEY
import com.example.gelderlandplein.ui.search.REQ_INFO_SHOP_KEY
import com.example.gelderlandplein.viewmodel.FirebaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_art_list.*
import kotlinx.android.synthetic.main.fragment_items_overview_carousel.*
import kotlinx.android.synthetic.main.fragment_search_list.*
import java.lang.Exception

const val REQ_ART_KEY = "req_art"
const val BUNDLE_ART_KEY = "bundle_art"

class HomeFragment : Fragment(), HomeEventAdapter.OnEventCardViewClickListener,
    HomeShopAdapter.OnShopsEventClickListener, HomeArtAdapter.OnArtCardViewClickListener {

    private val events = arrayListOf<Event>()
    private val eventAdapter = HomeEventAdapter(events, this)

    private val arts = arrayListOf<Art>()
    private val artAdapter = HomeArtAdapter(arts, this)

    private val shops = arrayListOf<Shop>()
    private val shopAdapter = HomeShopAdapter(shops, this)

    private val firebaseViewModel : FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseViewModel.getAllShops()
        firebaseViewModel.getAllEvents()
        firebaseViewModel.getAllArts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (shops.isNotEmpty() || events.isNotEmpty() || arts.isNotEmpty()){
            pb_loading_shops.isVisible = false
            pb_loading_events.isVisible = false
            pb_loading_arts.isVisible = false
        }

        rv_events_carousel.adapter = eventAdapter
        rv_arts_carousel.adapter = artAdapter
        rv_shops_carousel.adapter = shopAdapter

        observeEvents()
        observeArts()
        observeShops()
    }

    private fun observeEvents() {
        firebaseViewModel.events.observe(viewLifecycleOwner, {
            this@HomeFragment.events.addAll(it)
            pb_loading_events.isVisible = false
            eventAdapter.notifyDataSetChanged()
        })
    }

    private fun observeArts() {
        firebaseViewModel.arts.observe(viewLifecycleOwner, {
            this@HomeFragment.arts.clear()
            this@HomeFragment.arts.addAll(it)
            pb_loading_arts.isVisible = false
            artAdapter.notifyDataSetChanged()
        })
    }

    private fun observeShops() {
        firebaseViewModel.shops.observe(viewLifecycleOwner, {
            this@HomeFragment.shops.addAll(it)
            pb_loading_shops.isVisible = false
            shopAdapter.notifyDataSetChanged()
        })
    }

    override fun onEventCardViewClick(dummyEvent: Event, position: Int) {
        goEventToDetail(dummyEvent)
    }

    private fun goEventToDetail(event: Event) {
        setFragmentResult(
            REQ_EVENT_KEY,
            bundleOf(
                Pair(
                    BUNDLE_EVENT_KEY,
                    Event(
                        event.title,
                        event.image,
                        event.actieGeldig,
                        event.beschrijving,
                        event.link
                    )
                )
            )
        )
        findNavController().navigate(R.id.action_homeFragment_to_eventDetailFragment)
    }

    override fun onShopsCardViewClick(shop: Shop, position: Int) {
        goToShopDetail(shop)
    }

    override fun onArtCardViewClick(art: Art, position: Int) {
        goToArtDetail(art)
    }

    private fun goToArtDetail(art: Art) {
        setFragmentResult(
            com.example.gelderlandplein.ui.home.REQ_ART_KEY,
            bundleOf(
                Pair(
                    com.example.gelderlandplein.ui.home.BUNDLE_ART_KEY,
                    Art(art.name, art.image, art.beschrijving, art.artist)
                )
            )
        )
        findNavController().navigate(R.id.action_homeFragment_to_ArtDetailFragment)
    }


    private fun goToShopDetail(shop: Shop) {
        setFragmentResult(
            REQ_INFO_SHOP_KEY,
            bundleOf(
                Pair(
                    BUNDLE_INFO_SHOP_KEY,
                    Shop(
                        shop.name,
                        shop.tag,
                        shop.image,
                        shop.openingstijden,
                        shop.latitude,
                        shop.longitude,
                        shop.inventory
                    )
                )
            )
        )
        findNavController().navigate(R.id.action_homeFragment_to_shopDetailFragment)
    }
}