package com.example.gelderlandplein.ui.search

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.adapters.ShopAdapter
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.viewmodel.FirebaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search_list.*
import java.util.*
import kotlin.collections.ArrayList

class SearchListFragment : Fragment() {
    private val shops = arrayListOf<Shop>()
    private var shopsAdapter = ShopAdapter(shops, ::goToShopDetail)

    private val firebaseViewModel: FirebaseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseViewModel.getAllShops()

        setHasOptionsMenu(true)

        if (shops.isNotEmpty()) {
            pb_loading.isVisible = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_search_list.adapter = shopsAdapter

        observeShopsList()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchMenuItem = menu.findItem(R.id.btSearch)
        val toolbarTitle = menu.findItem(R.id.toolbar_title)
        val btnSearch = searchMenuItem.actionView as SearchView

        btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //change this is we want to do something when submit is clicked.
                return false
            }

            override fun onQueryTextChange(searchText: String): Boolean {
                search(searchText)
                return true
            }
        })
    }

    private fun search(searchText: String) {
        val filterdShops = ArrayList<Shop>()
        val filterQuery = searchText.trim().toLowerCase(Locale.ROOT)

        for (shop in shops) {
            val nameSearch = shop.name.toString().trim().toLowerCase(Locale.ROOT)
            val tagSearch = shop.tag.toString().trim().toLowerCase(Locale.ROOT)
            val inventorySearch = shop.inventory.toString().trim().toLowerCase(Locale.ROOT)

            if (nameSearch.contains(filterQuery) || tagSearch.contains(filterQuery)
                || inventorySearch.contains(filterQuery)
            ) {
                filterdShops.add(shop)
            }
            shopsAdapter = ShopAdapter(filterdShops, ::goToShopDetail)
            rv_search_list.adapter = shopsAdapter
        }
    }

    private fun observeShopsList() {
        firebaseViewModel.shops.observe(viewLifecycleOwner, {
            this@SearchListFragment.shops.clear()
            this@SearchListFragment.shops.addAll(it)
            pb_loading.isVisible = false
            shopsAdapter.notifyDataSetChanged()
        })
    }

    private fun goToShopDetail(shop: Shop) {
        firebaseViewModel.sendDetailShop(shop)
        findNavController().navigate(R.id.action_SearchFragment_to_shopDetailFragment)
    }
}