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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

const val REQ_ART_KEY = "req_art"
const val BUNDLE_ART_KEY = "bundle_art"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), HomeEventAdapter.OnEventCardViewClickListener,
    HomeShopAdapter.OnShopsEventClickListener, HomeArtAdapter.OnArtCardViewClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val events = arrayListOf<Event>()
    private val eventAdapter = HomeEventAdapter(events, this)
    private var eventDatabase: DatabaseReference = Firebase.database.reference.child("events")
    private var eventListener: ValueEventListener? = null

    private val artItems = arrayListOf<Art>()
    private val artAdapter = HomeArtAdapter(artItems, this)
    private var artDatabase: DatabaseReference = Firebase.database.reference.child("arts")
    private var artListener: ValueEventListener? = null

    private val shops = arrayListOf<Shop>()
    private val shopAdapter = HomeShopAdapter(shops, this)
    private var shopDatabase: DatabaseReference = Firebase.database.reference.child("shops")
    private var storeListener: ValueEventListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        if (shops.isNotEmpty() || events.isNotEmpty() || artItems.isNotEmpty()){
            pb_loading_home.isVisible = false
        }
        Firebase.database.setPersistenceEnabled(true)
        shopDatabase.keepSynced(true)
        eventDatabase.keepSynced(true)
        artDatabase.keepSynced(true)
        rv_events_carousel.adapter = eventAdapter
        rv_arts_carousel.adapter = artAdapter
        rv_shops_carousel.adapter = shopAdapter
        getAllEvents()
        getAllArt()
        getAllStores()
    }

    private fun getAllEvents() {
        this.eventListener = null
        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                events.clear()
                for (currentEvent: DataSnapshot in snapshot.children) {
                    try {
                        val event = Event(
                            currentEvent.child("name").value.toString(),
                            currentEvent.child("image").value.toString(),
                            currentEvent.child("geldigheid").value.toString(),
                            currentEvent.child("beschrijving").value.toString(),
                            currentEvent.child("link").value.toString()
                        )
                        events.add(event)
                    } catch (exception: Exception) {
                        Log.e(ContentValues.TAG, exception.toString())
                    }
                }
                eventAdapter.notifyDataSetChanged()
                if (pb_loading_home != null) {
                    pb_loading_home.isVisible = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, "Er gaat iets mis met het ophalen van de events")
            }

        }
        eventDatabase.addValueEventListener(eventListener)
        this.eventListener = eventListener
    }

    private fun getAllArt() {
        this.artListener = null
        val artListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                artItems.clear()
                for (currentArt: DataSnapshot in snapshot.children) {
                    try {
                        val art = Art(
                            currentArt.child("name").value.toString(),
                            currentArt.child("logo").value.toString(),
                            currentArt.child("beschrijving").value.toString(),
                            currentArt.child("artist").value.toString()
                        )
                        artItems.add(art)
                    } catch (exception: Exception) {
                        Log.e(ContentValues.TAG, exception.toString())
                    }
                }
                artAdapter.notifyDataSetChanged()
                if (pb_loading_home != null) {
                    pb_loading_home.isVisible = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, "Er gaat iets mis met het ophalen van de arts")
            }

        }
        artDatabase.addValueEventListener(artListener)
        this.artListener = artListener
    }

    private fun getAllStores() {
        this.storeListener = null
        val storeListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                shops.clear()
                for (currentShop: DataSnapshot in snapshot.children) {
                    val openingstijden = arrayListOf(
                        "Maandag: " + currentShop.child("openingstijden").child("maandag").value,
                        "Dinsdag: " + currentShop.child("openingstijden").child("dinsdag").value,
                        "Woensdag: " + currentShop.child("openingstijden").child("woensdag").value,
                        "Donderdag: " + currentShop.child("openingstijden")
                            .child("donderdag").value,
                        "Vrijdag: " + currentShop.child("openingstijden").child("vrijdag").value,
                        "Zaterdag: " + currentShop.child("openingstijden").child("zaterdag").value,
                        "Zondag: " + currentShop.child("openingstijden").child("zondag").value
                    )
                    val inventory = ArrayList<String>()
                    for (inventoryItem: DataSnapshot in currentShop.child("inventory").children) {
                        inventory.add(inventoryItem.value.toString())
                    }
                    try {
                        val shop = Shop(
                            currentShop.child("name").value.toString(),
                            currentShop.child("tag").value.toString(),
                            currentShop.child("logo").value.toString(),
                            openingstijden,
                            currentShop.child("latitude").value.toString().toDouble(),
                            currentShop.child("longitude").value.toString().toDouble(),
                            inventory
                        )
                        shops.add(shop)
                    } catch (exception: Exception) {
                        Log.e(ContentValues.TAG, exception.toString())
                    }
                }
                shopAdapter.notifyDataSetChanged()
                if (pb_loading_home != null) {
                    pb_loading_home.isVisible = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, "Er gaat iets mis met het ophalen van de stores")
            }

        }
        shopDatabase.addValueEventListener(storeListener)
        this.storeListener = storeListener
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment firstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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