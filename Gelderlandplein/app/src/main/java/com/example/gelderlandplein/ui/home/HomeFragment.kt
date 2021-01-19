package com.example.gelderlandplein.ui.home

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.adapters.*
import com.example.gelderlandplein.models.Art
import com.example.gelderlandplein.models.Event
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.repository.FirebaseRepository
import com.example.gelderlandplein.viewmodel.FirebaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_items_overview_carousel.*
import java.lang.reflect.Type

class HomeFragment : Fragment(){

    private val events = arrayListOf<Event>()
    private val eventAdapter = HomeEventAdapter(events, ::goEventToDetail)

    private val arts = arrayListOf<Art>()
    private val artAdapter = HomeArtAdapter(arts, ::goToArtDetail)

    private val shops = arrayListOf<Shop>()
    private val shopAdapter = HomeShopAdapter(shops, ::goToShopDetail)

    private var viewedShops = arrayListOf<String>()

    private val firebaseViewModel : FirebaseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        observeEvents()
        observeArts()
        observeShops()
        addShops()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        if (shops.isNotEmpty() || events.isNotEmpty() || arts.isNotEmpty()){
            pb_loading_shops.isVisible = false
            pb_loading_events.isVisible = false
            pb_loading_arts.isVisible = false
        }
        rv_events_carousel.adapter = eventAdapter
        rv_arts_carousel.adapter = artAdapter
        rv_shops_carousel.adapter = shopAdapter
        setAllButtonOnClickListeners()
    }

    private fun setAllButtonOnClickListeners(){
        btn_show_all_shops.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_SearchFragment)
        }
        btn_show_all_events.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_EventFragment)
        }
        btn_show_all_arts.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_ArtOverviewFragment)
        }
    }

    private fun observeEvents() {
        firebaseViewModel.events.observe(viewLifecycleOwner, {
            this@HomeFragment.events.addAll(it)
            pb_loading_events.isVisible = false
            btn_show_all_events.isVisible = true
            eventAdapter.notifyDataSetChanged()
        })
    }

    private fun observeArts() {
        firebaseViewModel.arts.observe(viewLifecycleOwner, {
            this@HomeFragment.arts.clear()
            this@HomeFragment.arts.addAll(it)
            pb_loading_arts.isVisible = false
            btn_show_all_arts.isVisible = true
            artAdapter.notifyDataSetChanged()
        })
    }

    private fun observeShops() {
//        firebaseViewModel.shops.observe(viewLifecycleOwner, {
//            this@HomeFragment.shops.addAll(it)
//            pb_loading_shops.isVisible = false
//            btn_show_all_shops.isVisible = true
//            shopAdapter.notifyDataSetChanged()
//        })
        firebaseViewModel.viewedShop.observe(viewLifecycleOwner, {
            this@HomeFragment.viewedShops.add(it)
            pb_loading_shops.isVisible = false
            btn_show_all_shops.isVisible = true
            saveData()
        })
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
        val maxSize = 5

        val sharedPreferences = activity?.getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val gson = Gson()
        if (viewedShops.size > maxSize) {
            viewedShops.remove(viewedShops.first())
        }
        viewedShops.reverse()
        firebaseViewModel.sendShopList(viewedShops)
        val json = gson.toJson(viewedShops)
        editor?.putString("shop list", json)
        editor?.apply()
    }

    private fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences?.getString("shop list", null)
        val type: Type = object : TypeToken<ArrayList<String>>() {}.type
        if (json != null){
            viewedShops = gson.fromJson(json, type)
        }
    }

    private fun addShops(){
        firebaseViewModel.lastSeenShops.observe(viewLifecycleOwner, {
            this@HomeFragment.shops.clear()
            this@HomeFragment.shops.addAll(it)
            shops.reverse()
            pb_loading_shops.isVisible = false
            btn_show_all_shops.isVisible = true
            shopAdapter.notifyDataSetChanged()
        })
    }
}