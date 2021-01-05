package com.example.gelderlandplein.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
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

class FirebaseRepository {

    private val _shops: MutableLiveData<ArrayList<Shop>> = MutableLiveData()
    private val _events: MutableLiveData<ArrayList<Event>> = MutableLiveData()
    private val _arts: MutableLiveData<ArrayList<Art>> = MutableLiveData()

    val shops: LiveData<ArrayList<Shop>> by lazy {
        getAllShops()
        _shops
    }
    val events: LiveData<ArrayList<Event>> by lazy {
        getAllEvents()
        _events
    }
    val arts: LiveData<ArrayList<Art>> by lazy {
        getAllArts()
        _arts
    }

    fun getAllShops() {
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

    fun getAllEvents() {
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

    fun getAllArts() {
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