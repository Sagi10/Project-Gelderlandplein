package com.example.gelderlandplein.ui.home

//import com.example.gelderlandplein.viewmodel.ShopViewModel

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.adapters.HomeArtAdapter
import com.example.gelderlandplein.adapters.HomeEventAdapter
import com.example.gelderlandplein.adapters.HomeShopAdapter
import com.example.gelderlandplein.models.Art
import com.example.gelderlandplein.models.Event
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.models.ShopList
import com.example.gelderlandplein.viewmodel.FirebaseViewModel
import com.example.gelderlandplein.viewmodel.ShopViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_items_overview_carousel.*
import java.lang.reflect.Type


class HomeFragment : Fragment(), HomeEventAdapter.OnEventCardViewClickListener,
    HomeShopAdapter.OnShopsEventClickListener, HomeArtAdapter.OnArtCardViewClickListener {


    private val events = arrayListOf<Event>()
    private val eventAdapter = HomeEventAdapter(events, this)

    private val arts = arrayListOf<Art>()
    private val artAdapter = HomeArtAdapter(arts, this)

    private var shops = arrayListOf<Shop>()
    private val shopAdapter = HomeShopAdapter(shops, this)

    private val firebaseViewModel : FirebaseViewModel by activityViewModels()
    private val shopViewModel: ShopViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseViewModel.getAllShops()
        firebaseViewModel.getAllEvents()
        firebaseViewModel.getAllArts()
        loadData()
        Log.d("SAVEDSIZE", shops.size.toString())
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
            Log.d("REFRESH","AAAAAAAAAAAAAAAAAAAAAAAART")
        })
    }

    private fun observeShops() {
        firebaseViewModel.viewedShop.observe(viewLifecycleOwner, { so ->
            Log.d("DITSHOP", so.toString())
            this@HomeFragment.shops.add(so)
            saveData()
        })
        pb_loading_shops.isVisible = false
        shopAdapter.notifyDataSetChanged()
    }

    override fun onEventCardViewClick(dummyEvent: Event, position: Int) {
        goEventToDetail(dummyEvent)
    }

    override fun onShopsCardViewClick(shop: Shop, position: Int) {
        goToShopDetail(shop)
    }

    override fun onArtCardViewClick(art: Art, position: Int) {
        goToArtDetail(art)
    }

    private fun goEventToDetail(event: Event) {
        firebaseViewModel.sendDetailEvent(event)
        findNavController().navigate(R.id.action_homeFragment_to_eventDetailFragment)
    }

    private fun goToArtDetail(art: Art) {
        firebaseViewModel.sendDetailArt(art)
        findNavController().navigate(R.id.action_homeFragment_to_ArtDetailFragment)
    }

    private fun goToShopDetail(shop: Shop) {
        firebaseViewModel.sendDetailShop(shop)
        findNavController().navigate(R.id.action_homeFragment_to_shopDetailFragment)
    }

    private fun saveData(){
        val sharedPreferences = activity?.getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val gson = Gson()
        val json = gson.toJson(shops)
        editor?.putString("shop list", json)
        editor?.apply()
        Log.d("SAVED3", json)
    }

    private fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences?.getString("shop list", null)
        val type: Type = object : TypeToken<ArrayList<Shop>>() {}.type
        if (json != null){
            shops = gson.fromJson(json, type)
        }
        Log.d("SAVED1", shops.toString())
    }
}