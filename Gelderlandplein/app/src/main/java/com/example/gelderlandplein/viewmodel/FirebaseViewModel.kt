package com.example.gelderlandplein.viewmodel

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gelderlandplein.models.Art
import com.example.gelderlandplein.models.Event
import com.example.gelderlandplein.models.Shop
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class FirebaseViewModel(application: Application) : AndroidViewModel(application) {


    private val _shops: MutableLiveData<ArrayList<Shop>> = MutableLiveData()
    private val _events: MutableLiveData<ArrayList<Event>> = MutableLiveData()
    private val _arts: MutableLiveData<ArrayList<Art>> = MutableLiveData()
    private val _lastSeenShops: MutableLiveData<ArrayList<Shop>> = MutableLiveData()

    private val _viewedShop: MutableLiveData<String> = MutableLiveData()
    private val _shopDetail: MutableLiveData<Shop> = MutableLiveData()
    private val _eventDetail: MutableLiveData<Event> = MutableLiveData()
    private val _artsDetail: MutableLiveData<Art> = MutableLiveData()

    val shops = _shops
    val events = _events
    val arts = _arts
    val lastSeenShops = _lastSeenShops

    val viewedShop = _viewedShop
    val shopDetail = _shopDetail
    val eventDetail = _eventDetail
    val artDetail = _artsDetail

    fun getAllShops() {
        viewModelScope.launch {
            if (_shops.value == null) {
                FirebaseDatabase.getInstance().reference.child("shops")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val allShops = ArrayList<Shop>()

                                if (snapshot.exists()) {
                                    for (currentShop: DataSnapshot in snapshot.children) {

                                        val openingstijden = arrayListOf(
                                                "Maandag: " + currentShop.child("openingstijden")
                                                        .child("maandag").value,
                                                "Dinsdag: " + currentShop.child("openingstijden")
                                                        .child("dinsdag").value,
                                                "Woensdag: " + currentShop.child("openingstijden")
                                                        .child("woensdag").value,
                                                "Donderdag: " + currentShop.child("openingstijden")
                                                        .child("donderdag").value,
                                                "Vrijdag: " + currentShop.child("openingstijden")
                                                        .child("vrijdag").value,
                                                "Zaterdag: " + currentShop.child("openingstijden")
                                                        .child("zaterdag").value,
                                                "Zondag: " + currentShop.child("openingstijden")
                                                        .child("zondag").value
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
                                                    currentShop.child("latitude").value.toString()
                                                            .toDouble(),
                                                    currentShop.child("longitude").value.toString()
                                                            .toDouble(),
                                                    inventory,
                                                    currentShop.child("website").value.toString()
                                            )
                                            allShops.add(shop)

                                        } catch (exception: Exception) {
                                            Log.e(ContentValues.TAG, exception.toString())
                                        }
                                    }
                                }
                                _shops.value = allShops
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.d(
                                        ContentValues.TAG,
                                        "Er gaat iets mis met het ophalen van de shops"
                                )
                            }
                        })
            }
        }
    }

    fun getAllEvents() {
        viewModelScope.launch {
            if (_events.value == null) {
                FirebaseDatabase.getInstance().reference.child("events")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val allEvents = ArrayList<Event>()

                                if (snapshot.exists()) {
                                    for (currentEvent: DataSnapshot in snapshot.children) {
                                        try {
                                            val event = Event(
                                                    currentEvent.child("name").value.toString(),
                                                    currentEvent.child("image").value.toString(),
                                                    currentEvent.child("geldigheid").value.toString(),
                                                    currentEvent.child("beschrijving").value.toString(),
                                                    currentEvent.child("link").value.toString()
                                            )
                                            allEvents.add(event)
                                        } catch (exception: java.lang.Exception) {
                                            Log.e(ContentValues.TAG, exception.toString())
                                        }
                                    }
                                }
                                _events.value = allEvents
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.d(
                                        ContentValues.TAG,
                                        "Er gaat iets mis met het ophalen van de events"
                                )
                            }
                        })
            }
        }
    }

    fun getAllArts() {
        viewModelScope.launch {
            if (_arts.value == null) {
                FirebaseDatabase.getInstance().reference.child("arts")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val allArts = ArrayList<Art>()

                                if (snapshot.exists()) {
                                    for (currentArt: DataSnapshot in snapshot.children) {
                                        try {
                                            val art = Art(
                                                    currentArt.child("name").value.toString(),
                                                    currentArt.child("logo").value.toString(),
                                                    currentArt.child("beschrijving").value.toString(),
                                                    currentArt.child("artist").value.toString()
                                            )
                                            allArts.add(art)
                                        } catch (exception: java.lang.Exception) {
                                            Log.e(ContentValues.TAG, exception.toString())
                                        }
                                    }
                                }
                                _arts.value = allArts
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.d(ContentValues.TAG, "Er gaat iets mis met het ophalen van de arts")
                            }
                        })
            }
        }
    }

    fun getShop(shopNames: ArrayList<String>) {
        viewModelScope.launch {
            FirebaseDatabase.getInstance().reference.child("shops")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val allShops = ArrayList<Shop>()

                            if (snapshot.exists()) {
                                for (currentShop: DataSnapshot in snapshot.children) {
                                    for (currentName in shopNames){
                                        if (currentShop.child("name").value.toString() == currentName) {
                                            val openingstijden = arrayListOf(
                                                    "Maandag: " + currentShop.child("openingstijden")
                                                            .child("maandag").value,
                                                    "Dinsdag: " + currentShop.child("openingstijden")
                                                            .child("dinsdag").value,
                                                    "Woensdag: " + currentShop.child("openingstijden")
                                                            .child("woensdag").value,
                                                    "Donderdag: " + currentShop.child("openingstijden")
                                                            .child("donderdag").value,
                                                    "Vrijdag: " + currentShop.child("openingstijden")
                                                            .child("vrijdag").value,
                                                    "Zaterdag: " + currentShop.child("openingstijden")
                                                            .child("zaterdag").value,
                                                    "Zondag: " + currentShop.child("openingstijden")
                                                            .child("zondag").value
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
                                                        currentShop.child("latitude").value.toString()
                                                                .toDouble(),
                                                        currentShop.child("longitude").value.toString()
                                                                .toDouble(),
                                                        inventory,
                                                        currentShop.child("website").value.toString()
                                                )
                                                allShops.add(shop)

                                            } catch (exception: Exception) {
                                                Log.e(ContentValues.TAG, exception.toString())
                                            }
                                        }
                                    }
                                }
                            }
                            _lastSeenShops.value = allShops
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.d(
                                    ContentValues.TAG,
                                    "Er gaat iets mis met het ophalen van de shops"
                            )
                        }
                    })
        }
    }

    fun sendLastViewed(shop: String?){
        viewModelScope.launch {
            try {
                _viewedShop.value = shop
            } catch (e: Throwable) {
                Log.e(TAG, "ERROR MET VERSTUREN DETAIL SHOP: ${e.message}")
            }
        }
    }

    fun sendDetailShop(shopDetail: Shop) {
        viewModelScope.launch {
            try {
                _shopDetail.value = shopDetail
            } catch (e: Throwable) {
                Log.e(TAG, "ERROR MET VERSTUREN DETAIL SHOP: ${e.message}")
            }
        }
    }

    fun sendDetailEvent(eventDetail: Event) {
        viewModelScope.launch {
            try {
                _eventDetail.value = eventDetail
            } catch (e: Throwable){
                Log.e(TAG, "ERROR MET VERSTUREN DETAIL EVENT: ${e.message}")
            }
        }
    }

    fun sendDetailArt(artDetail: Art) {
        viewModelScope.launch {
            try {
                _artsDetail.value = artDetail
            } catch (e: Throwable){
                Log.e(TAG, "ERROR MET VERSTUREN DETAIL ART: ${e.message}")
            }
        }
    }
}